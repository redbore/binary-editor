package ru.editor.binaryeditor.core.services.type;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class FieldHandlerFactory {

    private final Map<String, FieldHandler> handlers = new HashMap<>();

    private void init() {
        handlers.put("int32", new FieldHandler(this::readInt32, this::writeInt32));
        handlers.put("float32", new FieldHandler(this::readFloat32, this::writeFloat32));
        handlers.put("string", new FieldHandler(this::readString, this::writeString));
    }

    public FieldReader reader(String type) {
        return handlers.get(type).reader();
    }

    public FieldWriter writer(String type) {
        return handlers.get(type).writer();
    }

    private Integer readInt32(byte[] bytes, AtomicInteger offset, Integer length) {
        int value = ByteBuffer.wrap(bytes, offset.get(), Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).getInt();
        offset.addAndGet(length);
        return value;
    }

    private Float readFloat32(byte[] bytes, AtomicInteger offset, Integer length) {
        float value = ByteBuffer.wrap(bytes, offset.get(), Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).getFloat();
        offset.addAndGet(length);
        return value;
    }

    private String readString(byte[] bytes, AtomicInteger offset, Integer length) {
        String value = StandardCharsets.UTF_8.decode(ByteBuffer.wrap(bytes, offset.get(), length)).toString().trim();
        offset.addAndGet(length);
        return value;
    }

    private void writeInt32(ByteBuffer buffer, Object value, Integer length) {
        buffer.order(ByteOrder.LITTLE_ENDIAN).putInt(toInt(value));
    }

    private void writeFloat32(ByteBuffer buffer, Object value, Integer length) {
        buffer.order(ByteOrder.LITTLE_ENDIAN).putFloat(toFloat(value));
    }

    private void writeString(ByteBuffer buffer, Object value, Integer length) {
        buffer.order(ByteOrder.BIG_ENDIAN).put(fixSizeString(toString(value), length).getBytes());
    }

    private static Integer toInt(Object value) {
        if (value instanceof String) {
            return Integer.parseInt((String) value);
        }
        if (value instanceof Integer) {
            return (Integer) value;
        }
        return 0;
    }

    private static Float toFloat(Object value) {
        if (value instanceof String) {
            return Float.parseFloat((String) value);
        }
        if (value instanceof Float) {
            return (Float) value;
        }
        return 0F;
    }

    private static String toString(Object value) {
        return value instanceof String ? (String) value : value.toString();
    }

    private static String fixSizeString(String value, Integer length) {
        StringBuilder stringBuilder = new StringBuilder(value);
        if (value.length() < length) {
            int needSize = length - value.length();
            for (int i = 0; i < needSize; i++) {
                stringBuilder.append("\u0000");
            }
        }
        return stringBuilder.toString();
    }
}
