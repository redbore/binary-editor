package ru.editor.binaryeditor.core.services;

import java.nio.file.Path;

public interface SettingsService {

  void saveXmlFilePath(String path);

  void saveBinaryFilePath(String path);

  Path getXmlFilePath();

  Path getBinaryFilePath();
}
