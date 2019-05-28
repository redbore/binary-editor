package ru.editor.binaryeditor.core.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Getter
@Setter
@Builder
@Accessors(fluent = true)
public class Field {

    @NonNull
    private final UUID uuid;

    @NonNull
    private final String name;

    @NonNull
    private Object value;
}
