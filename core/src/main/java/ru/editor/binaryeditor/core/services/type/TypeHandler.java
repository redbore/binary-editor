package ru.editor.binaryeditor.core.services.type;

import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class TypeHandler {

    private final TypeReader reader;

    private final TypeWriter writer;

    public TypeHandler(TypeReader reader, TypeWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }
}
