package ru.editor.binaryeditor.core.services;

import ru.editor.binaryeditor.core.services.interfaces.TypeReader;
import ru.editor.binaryeditor.core.services.interfaces.TypeWriter;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Byte.parseByte;
import static java.lang.Double.parseDouble;
import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import static java.lang.Short.parseShort;
import static ru.editor.binaryeditor.core.services.Type.*;

public final class TypeHandler {

    private static Map<Type, TypeReader> readers = new HashMap<>();
    private static Map<Type, TypeWriter> writers = new HashMap<>();

    private static TypeReader readInt8 = (bytes, offset, length) -> readNumber(bytes, offset, length).get();
    private static TypeReader readInt16 = (bytes, offset, length) -> readNumber(bytes, offset, length).getShort();
    private static TypeReader readInt32 = (bytes, offset, length) -> readNumber(bytes, offset, length).getInt();
    private static TypeReader readInt64 = (bytes, offset, length) -> readNumber(bytes, offset, length).getLong();
    private static TypeReader readFloat32 = (bytes, offset, length) -> readNumber(bytes, offset, length).getFloat();
    private static TypeReader readFloat64 = (bytes, offset, length) -> readNumber(bytes, offset, length).getDouble();

    private static TypeWriter writeInt8 = (buffer, value, length) -> writeNumber(buffer).put(parseByte(value));
    private static TypeWriter writeInt16 = (buffer, value, length) -> writeNumber(buffer).putShort(parseShort(value));
    private static TypeWriter writeInt32 = (buffer, value, length) -> writeNumber(buffer).putInt(parseInt(value));
    private static TypeWriter writeInt64 = (buffer, value, length) -> writeNumber(buffer).putLong(parseLong(value));
    private static TypeWriter writeFloat32 = (buffer, value, length) -> writeNumber(buffer).putFloat(parseFloat(value));
    private static TypeWriter writeFloat64 = (buffer, value, length) -> writeNumber(buffer).putDouble(parseDouble(value));

    static {
        putHandler(INT8, readInt8, writeInt8);
        putHandler(INT16, readInt16, writeInt16);
        putHandler(INT32, readInt32, writeInt32);
        putHandler(INT64, readInt64, writeInt64);
        putHandler(FLOAT32, readFloat32, writeFloat32);
        putHandler(FLOAT64, readFloat64, writeFloat64);
        putHandler(STRING, TypeHandler::readString, TypeHandler::writeString);
    }

    public static TypeReader reader(Type type) {
        return Optional.ofNullable(readers.get(type))
                .orElseThrow(() -> new RuntimeException("TypeReader not found:" + type.typeName()));
    }

    public static TypeWriter writer(Type type) {
        return Optional.ofNullable(writers.get(type))
                .orElseThrow(() -> new RuntimeException("TypeWriter not found: typeName:" + type.typeName()));
    }

    private static void putHandler(Type type, TypeReader typeReader, TypeWriter typeWriter) {
        readers.put(type, typeReader);
        writers.put(type, typeWriter);
    }

    private static Object readString(byte[] bytes, AtomicInteger offset, Integer length) {
        String value = StandardCharsets.UTF_8.decode(ByteBuffer.wrap(bytes, offset.get(), length)).toString().trim();
        offset.addAndGet(length);
        return value;
    }

    private static ByteBuffer readNumber(byte[] bytes, AtomicInteger offset, Integer length) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes, offset.get(), length).order(ByteOrder.LITTLE_ENDIAN);
        offset.addAndGet(length);
        return buffer;
    }

    private static void writeString(ByteBuffer buffer, String value, Integer length) {
        buffer.order(ByteOrder.BIG_ENDIAN).put(toFixSizeString(value, length).getBytes());
    }

    private static ByteBuffer writeNumber(ByteBuffer buffer) {
        return buffer.order(ByteOrder.LITTLE_ENDIAN);
    }

    private static String toFixSizeString(String value, Integer length) {
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
