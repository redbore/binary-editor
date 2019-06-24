package ru.editor.binaryeditor.core.services.interfaces;

import java.util.UUID;

public interface FileReader {

    void read(UUID binaryFileId, UUID specificationId) throws Exception;
}
