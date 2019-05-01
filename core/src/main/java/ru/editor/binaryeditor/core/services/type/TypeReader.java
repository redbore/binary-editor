package ru.editor.binaryeditor.core.services.type;

import java.util.concurrent.atomic.AtomicInteger;

public interface TypeReader {

    Object read(byte[] bytes, AtomicInteger offset, Integer length);
}
