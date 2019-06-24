package ru.editor.binaryeditor.core.services.interfaces;

import java.util.concurrent.atomic.AtomicInteger;

public interface TypeReader {
    Object read(byte[] bytes, AtomicInteger offset, Integer length);
}
