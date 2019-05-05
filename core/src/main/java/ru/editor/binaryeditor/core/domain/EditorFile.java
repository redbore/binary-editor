package ru.editor.binaryeditor.core.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EditorFile {

    private final String name;

    private final int[] body;
}

