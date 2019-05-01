package ru.editor.binaryeditor.core.services.type;

import java.nio.ByteBuffer;

public interface FieldWriter {
    void write(ByteBuffer byteBuffer, Object value, Integer length);
}
