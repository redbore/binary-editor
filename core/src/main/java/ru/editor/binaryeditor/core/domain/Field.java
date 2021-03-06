package ru.editor.binaryeditor.core.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.UUID;

@Getter
@Builder
@ToString
@Accessors(fluent = true)
@EqualsAndHashCode
public class Field {
    private UUID id;

    private UUID binaryFileId;

    private UUID fieldDescriptionId;

    private String value;

    private int length;
}
