package ru.editor.binaryeditor.server.services;

import lombok.RequiredArgsConstructor;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.ex.ConfigurationException;
import ru.editor.binaryeditor.core.domain.Paths;
import ru.editor.binaryeditor.core.services.SettingsService;

@RequiredArgsConstructor
public class SettingsServiceImpl implements SettingsService {

  private final FileBasedConfiguration configuration;

  private final FileBasedConfigurationBuilder<FileBasedConfiguration> configurationBuilder;

  public void savePaths(Paths paths) {
    try {
      configuration.setProperty("xmlFilePath", paths.xml());
      configuration.setProperty("binaryFilePath", paths.binary());
      configurationBuilder.save();
    } catch (ConfigurationException ignored) {
    }
  }

  public Paths paths() {
    String xmlFilePath = configuration.getString("xmlFilePath");
    String binaryFilePath = configuration.getString("binaryFilePath");
    return Paths.builder()
            .xml(xmlFilePath == null ? "" : xmlFilePath)
            .binary(binaryFilePath == null ? "" : binaryFilePath)
            .build();
  }
}
