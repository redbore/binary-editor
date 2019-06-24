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
public class Segment {

    private UUID id;

    private UUID specificationId;

    private String name;

    private String countLink;
}
