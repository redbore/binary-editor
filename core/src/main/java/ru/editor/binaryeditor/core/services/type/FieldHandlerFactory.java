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
        handlers.put("int16", new FieldHandler(this::readInt16, this::writeInt16));
        handlers.put("int32", new FieldHandler(this::readInt32, this::writeInt32));
        handlers.put("int64", new FieldHandler(this::readInt64, this::writeInt64));
        handlers.put("float32", new FieldHandler(this::readFloat32, this::writeFloat32));
        handlers.put("float64", new FieldHandler(this::readFloat64, this::writeFloat64));
        handlers.put("string", new FieldHandler(this::readString, this::writeString));
    }

    public FieldReader reader(String type) {
        return handlers.get(type).reader();
    }

    public FieldWriter writer(String type) {
        return handlers.get(type).writer();
    }

    private Short readInt16(byte[] bytes, AtomicInteger offset, Integer length) {
        short value = ByteBuffer.wrap(bytes, offset.get(), Short.BYTES).order(ByteOrder.LITTLE_ENDIAN).getShort();
        offset.addAndGet(length);
        return value;
    }

    private Integer readInt32(byte[] bytes, AtomicInteger offset, Integer length) {
        int value = ByteBuffer.wrap(bytes, offset.get(), Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).getInt();
        offset.addAndGet(length);
        return value;
    }

    private Long readInt64(byte[] bytes, AtomicInteger offset, Integer length) {
        long value = ByteBuffer.wrap(bytes, offset.get(), Long.BYTES).order(ByteOrder.LITTLE_ENDIAN).getLong();
        offset.addAndGet(length);
        return value;
    }

    private Float readFloat32(byte[] bytes, AtomicInteger offset, Integer length) {
        float value = ByteBuffer.wrap(bytes, offset.get(), Float.BYTES).order(ByteOrder.LITTLE_ENDIAN).getFloat();
        offset.addAndGet(length);
        return value;
    }

    private Double readFloat64(byte[] bytes, AtomicInteger offset, Integer length) {
        double value = ByteBuffer.wrap(bytes, offset.get(), Double.BYTES).order(ByteOrder.LITTLE_ENDIAN).getDouble();
        offset.addAndGet(length);
        return value;
    }

    private String readString(byte[] bytes, AtomicInteger offset, Integer length) {
        String value = StandardCharsets.UTF_8.decode(ByteBuffer.wrap(bytes, offset.get(), length)).toString().trim();
        offset.addAndGet(length);
        return value;
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
