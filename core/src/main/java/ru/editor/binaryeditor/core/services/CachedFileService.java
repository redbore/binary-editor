package ru.editor.binaryeditor.core.services;

import java.io.IOException;
import java.nio.file.Path;

public interface CachedFileService {

    Path updateBinary(String name, byte[] body) throws IOException;

    Path updateBinary(byte[] body) throws IOException;

    Path updateSpecification(String name, byte[] body) throws IOException;

    byte[] getBinaryFile() throws IOException;

    Path binaryPath() throws IOException;

    Path specificationPath() throws IOException;

}


