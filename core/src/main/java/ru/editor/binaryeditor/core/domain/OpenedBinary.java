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
public class OpenedBinary {

    @NonNull
    private final UUID uuid;

    @NonNull
    private final List<Type> types;

    public Integer size(Specification specification) {
        return specification.xmlSegments().stream()
                .map(xmlSegment -> xmlSegment.xmlFields().stream()
                        .mapToInt(xmlField -> xmlField.length() * instanceSize(xmlSegment.name()))
                        .reduce((a, b) -> a + b))
                .mapToInt(OptionalInt::getAsInt)
                .reduce((a, b) -> a + b)
                .orElseThrow(() -> new RuntimeException("File size error"));
    }

    private Integer instanceSize(String typeName) {
        return type(typeName).instances().size();
    }

    public Type type(String typeName) {
        return types.stream()
                .filter(type -> type.name().equals(typeName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Type not found: name=" + typeName));
    }

    public Type type(UUID uuid) {
        return types.stream()
                .filter(type -> type.uuid().equals(uuid))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Type not found: id=" + uuid));
    }

    public Integer findByLink(String link) {
        if (types.isEmpty()) {
            throw new RuntimeException();
        }
        String[] names = link.split("[.]");
        return types
                .stream()
                .filter(tempType -> tempType.name().equals(names[0]))
                .findFirst()
                .map(Type::instances)
                .flatMap(instances -> instances
                        .stream()
                        .findFirst()
                        .flatMap(instance -> instance.fields()
                                .stream()
                                .filter(field -> field.name().equals(names[1]))
                                .findFirst()
                                .map(field -> (Integer) field.value()))).get();
    }
}
