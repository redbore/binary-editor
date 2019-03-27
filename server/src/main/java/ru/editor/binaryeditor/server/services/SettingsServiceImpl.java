package ru.editor.binaryeditor.server.services;

import java.nio.file.Path;
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
  public void saveXmlFilePath(String path) {
    try {
      fileBasedConfiguration.setProperty("xmlFilePath", path);
      fileBasedConfigurationBuilder.save();
    } catch (ConfigurationException ignored) {
    }
  }

  @Override
  public void saveBinaryFilePath(String path) {
    try {
      fileBasedConfiguration.setProperty("binaryFilePath", path);
      fileBasedConfigurationBuilder.save();
    } catch (ConfigurationException ignored) {
    }
  }

  @Override
  public Path getXmlFilePath() {
    String xmlFilePath = fileBasedConfiguration.getString("xmlFilePath");
    return Path.of(xmlFilePath == null ? "" : xmlFilePath);
  }

  @Override
  public Path getBinaryFilePath() {
    String binaryFilePath = fileBasedConfiguration.getString("binaryFilePath");
    return Path.of(binaryFilePath == null ? "" : binaryFilePath);
  }
}
