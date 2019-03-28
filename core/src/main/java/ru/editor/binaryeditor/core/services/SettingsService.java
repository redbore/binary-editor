package ru.editor.binaryeditor.core.services;

public interface SettingsService {

  String saveXmlFilePath(String path);

  String saveBinaryFilePath(String path);

  String getXmlFilePath();

  String getBinaryFilePath();
}
