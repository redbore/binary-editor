package ru.editor.binaryeditor.core.services.type;

import java.util.concurrent.atomic.AtomicInteger;

public interface FieldReader {
    Object read(byte[] bytes, AtomicInteger offset, Integer length);
}
