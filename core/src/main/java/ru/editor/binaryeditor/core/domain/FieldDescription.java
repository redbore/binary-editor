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
public class FieldDescription {
    private UUID id;

    private UUID segmentId;

    private String name;

    private String type;

    private String lengthLink;
}
