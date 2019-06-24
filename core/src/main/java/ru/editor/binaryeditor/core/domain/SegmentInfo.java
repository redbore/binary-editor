package ru.editor.binaryeditor.core.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.List;

@Builder
@Getter
@Accessors(fluent = true)
public class SegmentInfo {

    private final List<FieldDescription> fieldDescriptions;

    private final Long fieldCount;
}
