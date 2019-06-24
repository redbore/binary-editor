package ru.editor.binaryeditor.core.services.interfaces;

import java.nio.ByteBuffer;

public interface TypeWriter {
    void write(ByteBuffer byteBuffer, String value, Integer length);
}
