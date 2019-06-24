package ru.editor.binaryeditor.core.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.Collections;
import java.util.Map;

@Getter
@Accessors(fluent = true)
@Builder
public class View {
    private BinaryFile binaryFile;

    private Specification specification;

    private Map<Segment, SegmentInfo> segments;

    public static View empty() {
        return View.builder()
                .segments(Collections.emptyMap())
                .build();
    }
}
