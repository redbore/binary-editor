package ru.editor.binaryeditor.core.services;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
public enum Type {
    INT8("int8", Byte.BYTES),
    INT16("int16", Short.BYTES),
    INT32("int32", Integer.BYTES),
    INT64("int64", Long.BYTES),
    FLOAT32("float32", Float.BYTES),
    FLOAT64("float64", Double.BYTES),
    STRING("string", 0);

    private final String typeName;
    private final Integer length;

    private static final Map<String, Type> matching = Stream.of(values())
            .collect(Collectors.toMap(Type::typeName, type -> type));

    public static Type type(String type) {
        return Optional.ofNullable(matching.get(type))
                .orElseThrow(() -> new RuntimeException("Type not found: typeName:" + type));
    }
}
