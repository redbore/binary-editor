package ru.editor.binaryeditor.core.services.type;

import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class FieldHandler {

    private final FieldReader reader;

    private final FieldWriter writer;

    public FieldHandler(FieldReader reader, FieldWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }
}
