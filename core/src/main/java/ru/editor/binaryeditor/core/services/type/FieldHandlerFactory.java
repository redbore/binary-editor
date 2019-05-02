package ru.editor.binaryeditor.core.services.type;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.editor.binaryeditor.core.services.type.TypeConverter.*;

public class FieldHandlerFactory {

    private final Map<String, FieldHandler> handlers = new HashMap<>();

    private void init() {
        handlers.put("int16", new FieldHandler(this::readInt16, this::writeInt16, Short.BYTES));
        handlers.put("int32", new FieldHandler(this::readInt32, this::writeInt32, Integer.BYTES));
        handlers.put("int64", new FieldHandler(this::readInt64, this::writeInt64, Long.BYTES));
        handlers.put("float32", new FieldHandler(this::readFloat32, this::writeFloat32, Float.BYTES));
        handlers.put("float64", new FieldHandler(this::readFloat64, this::writeFloat64, Double.BYTES));
        handlers.put("string", new FieldHandler(this::readString, this::writeString, 0));
    }

    public FieldReader reader(String type) {
        return handlers.get(type).reader();
    }

    public FieldWriter writer(String type) {
        return handlers.get(type).writer();
    }

    public Integer length(String type) {
        return handlers.get(type).length();
    }

    private Object readInt16(byte[] bytes, AtomicInteger offset, Integer length) {
        return readNumber(bytes, offset, length).getShort();
    }

    private Object readInt32(byte[] bytes, AtomicInteger offset, Integer length) {
        return readNumber(bytes, offset, length).getInt();
    }

    private Object readInt64(byte[] bytes, AtomicInteger offset, Integer length) {
        return readNumber(bytes, offset, length).getLong();
    }

    private Object readFloat32(byte[] bytes, AtomicInteger offset, Integer length) {
        return readNumber(bytes, offset, length).getFloat();
    }

    private Object readFloat64(byte[] bytes, AtomicInteger offset, Integer length) {
        return readNumber(bytes, offset, length).getDouble();
    }

    private Object readString(byte[] bytes, AtomicInteger offset, Integer length) {
        String value = StandardCharsets.UTF_8.decode(ByteBuffer.wrap(bytes, offset.get(), length)).toString().trim();
        offset.addAndGet(length);
        return value;
    }

    private static ByteBuffer readNumber(byte[] bytes, AtomicInteger offset, Integer length) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes, offset.get(), length).order(ByteOrder.LITTLE_ENDIAN);
        offset.addAndGet(length);
        return buffer;
    }

    private void writeInt16(ByteBuffer buffer, Object value, Integer length) {
        buffer.order(ByteOrder.LITTLE_ENDIAN).putShort(toShort(value));
    }

    private void writeInt32(ByteBuffer buffer, Object value, Integer length) {
        buffer.order(ByteOrder.LITTLE_ENDIAN).putInt(toInt(value));
    }

    private void writeInt64(ByteBuffer buffer, Object value, Integer length) {
        buffer.order(ByteOrder.LITTLE_ENDIAN).putLong(toLong(value));
    }

    private void writeFloat32(ByteBuffer buffer, Object value, Integer length) {
        buffer.order(ByteOrder.LITTLE_ENDIAN).putFloat(toFloat(value));
    }

    private void writeFloat64(ByteBuffer buffer, Object value, Integer length) {
        buffer.order(ByteOrder.LITTLE_ENDIAN).putDouble(toDouble(value));
    }

    private void writeString(ByteBuffer buffer, Object value, Integer length) {
        buffer.order(ByteOrder.BIG_ENDIAN).put(toFixSizeString(TypeConverter.toString(value), length).getBytes());
    }

}
