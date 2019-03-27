package ru.editor.binaryeditor.server.conf;

import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.editor.binaryeditor.core.services.BinaryFileReader;
import ru.editor.binaryeditor.core.services.BinaryFileWriter;
import ru.editor.binaryeditor.core.services.EditorOperations;
import ru.editor.binaryeditor.core.services.SettingsService;
import ru.editor.binaryeditor.core.services.XmlFileReader;
import ru.editor.binaryeditor.server.services.SettingsServiceImpl;

@Configuration
public class CommonConfiguration {

  @Bean
  public FileBasedConfigurationBuilder fileBasedConfigurationBuilder() {
    return new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
        .configure(new Parameters()
            .properties()
            .setFileName("settings.txt"));
  }

  @Bean
  public FileBasedConfiguration fileBasedConfiguration(
      FileBasedConfigurationBuilder<FileBasedConfiguration> fileBasedConfigurationBuilder
  ) throws ConfigurationException {
    return fileBasedConfigurationBuilder.getConfiguration();
  }

  @Bean
  public SettingsService settingsService(
      FileBasedConfiguration fileBasedConfiguration,
      FileBasedConfigurationBuilder<FileBasedConfiguration> fileBasedConfigurationBuilder
  ) {
    return new SettingsServiceImpl(fileBasedConfiguration, fileBasedConfigurationBuilder);
  }

  @Bean
  public XmlFileReader xmlFileReader(SettingsService settingsService) {
    return new XmlFileReader(settingsService);
  }

  @Bean
  public BinaryFileReader binaryFileReader(
      SettingsService settingsService,
      XmlFileReader xmlFileReader
  ) {
    return new BinaryFileReader(settingsService, xmlFileReader);
  }

  @Bean
  public BinaryFileWriter binaryFileWriter(
      SettingsService settingsService,
      XmlFileReader xmlFileReader
  ) {
    return new BinaryFileWriter(settingsService, xmlFileReader);
  }

  @Bean
  public EditorOperations editorOperations(
      BinaryFileReader binaryFileReader,
      BinaryFileWriter binaryFileWriter,
      SettingsService settingsService
  ) {
    return new EditorOperations(binaryFileReader, binaryFileWriter, settingsService);
  }
}
