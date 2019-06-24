package ru.editor.binaryeditor.core.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Builder
@Accessors(fluent = true)
public class FieldLink {

    private final String fieldName;

    private final String segmentName;

}
