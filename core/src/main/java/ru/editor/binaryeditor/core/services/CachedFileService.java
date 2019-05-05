package ru.editor.binaryeditor.core.services;

import java.io.IOException;
import java.nio.file.Path;

public interface CachedFileService {

    Path updateBinaryFile(String name, byte[] body) throws IOException;

    Path updateXmlFile(String name, byte[] body) throws IOException;

    Path binaryPath() throws IOException;

    Path xmlPath() throws IOException;

}


