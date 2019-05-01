package ru.editor.binaryeditor.core.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.OptionalInt;
import java.util.UUID;

@Getter
@Builder
@Accessors(fluent = true)
public class BinaryFile {

    @NonNull
    private final UUID uuid;

    @NonNull
    private final List<Type> types;

    public Integer getSize(XmlFile xmlFile) {
        return xmlFile.xmlSegments().stream()
                .map(xmlSegment -> xmlSegment.xmlFields().stream()
                        .mapToInt(xmlField -> xmlField.length() * getInstanceSize(xmlSegment.name()))
                        .reduce((a, b) -> a + b))
                .mapToInt(OptionalInt::getAsInt)
                .reduce((a, b) -> a + b)
                .orElseThrow(() -> new RuntimeException("File size error"));
    }

    private Integer getInstanceSize(String typeName) {
        return getType(typeName).instances().size();
    }

    public Type getType(String typeName) {
        return types.stream()
                .filter(type -> type.name().equals(typeName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Type not found: name=" + typeName));
    }

    public Type getType(UUID uuid) {
        return types.stream()
                .filter(type -> type.uuid().equals(uuid))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Type not found: id=" + uuid));
    }
}
