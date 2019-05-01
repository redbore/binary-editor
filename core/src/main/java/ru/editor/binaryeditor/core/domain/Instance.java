package ru.editor.binaryeditor.core.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
@Accessors(fluent = true)
public class Instance {

    @NonNull
    private final UUID uuid;

    @NonNull
    private final List<Field> fields;


    public Field findField(UUID fieldId) {
        return fields.stream()
                .filter(field -> field.uuid().equals(fieldId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Field not found"));
    }
}
