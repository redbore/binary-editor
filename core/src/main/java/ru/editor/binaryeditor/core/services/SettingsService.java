package ru.editor.binaryeditor.core.services;

import java.nio.file.Path;

public interface SettingsService {

  Path saveXmlFilePath(Path path);

  Path saveBinaryFilePath(Path path);

  Path getXmlFilePath();

  Path getBinaryFilePath();
}
