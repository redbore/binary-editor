package ru.editor.binaryeditor.server.services;

import lombok.RequiredArgsConstructor;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.ex.ConfigurationException;
import ru.editor.binaryeditor.core.services.SettingsService;

@RequiredArgsConstructor
public class SettingsServiceImpl implements SettingsService {

  private final FileBasedConfiguration fileBasedConfiguration;

  private final FileBasedConfigurationBuilder<FileBasedConfiguration> fileBasedConfigurationBuilder;

  @Override
  public String saveXmlFilePath(String path) {
    try {
      fileBasedConfiguration.setProperty("xmlFilePath", path);
      fileBasedConfigurationBuilder.save();
    } catch (ConfigurationException ignored) {
    }
    return path;
  }

  @Override
  public String saveBinaryFilePath(String path) {
    try {
      fileBasedConfiguration.setProperty("binaryFilePath", path);
      fileBasedConfigurationBuilder.save();
    } catch (ConfigurationException ignored) {
    }
    return path;
  }

  @Override
  public String getXmlFilePath() {
    String xmlFilePath = fileBasedConfiguration.getString("xmlFilePath");
    return xmlFilePath == null ? "" : xmlFilePath;
  }

  @Override
  public String getBinaryFilePath() {
    String binaryFilePath = fileBasedConfiguration.getString("binaryFilePath");
    return binaryFilePath == null ? "" : binaryFilePath;
  }
}
