package ru.editor.binaryeditor.core.services.interfaces;

import java.util.UUID;

public interface FileWriter {

    void write(UUID binaryFileId, UUID specificationId);
}
