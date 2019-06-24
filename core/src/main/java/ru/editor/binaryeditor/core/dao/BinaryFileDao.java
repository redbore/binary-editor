package ru.editor.binaryeditor.core.dao;

import ru.editor.binaryeditor.core.domain.BinaryFile;

import java.util.Optional;
import java.util.UUID;

public interface BinaryFileDao {

    byte[] getBody();

    void updateBody(UUID id, byte[] body);

    void insert(BinaryFile binaryFile);

    void clean();

    Optional<BinaryFile> find();

    BinaryFile getWithBody();

    BinaryFile get();
}
