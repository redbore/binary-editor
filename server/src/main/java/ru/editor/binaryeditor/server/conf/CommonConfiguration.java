package ru.editor.binaryeditor.server.conf;

import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.editor.binaryeditor.core.services.*;
import ru.editor.binaryeditor.core.services.type.TypeHandlerFactory;
import ru.editor.binaryeditor.server.services.SettingsServiceImpl;

@Configuration
public class CommonConfiguration {

    @Bean(initMethod = "init")
    public TypeHandlerFactory typeHandlerFactory() {
        return new TypeHandlerFactory();
    }

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
    public XmlFileReader xmlFileReader() {
        return new XmlFileReader();
    }

    @Bean
    public BinaryFileReader binaryFileReader(TypeHandlerFactory typeHandlerFactory) {
        return new BinaryFileReader(typeHandlerFactory);
    }

    @Bean
    public BinaryFileWriter binaryFileWriter(
            XmlFileReader xmlFileReader,
            TypeHandlerFactory typeHandlerFactory
    ) {
        return new BinaryFileWriter(xmlFileReader, typeHandlerFactory);
    }

    @Bean
    public Editor editorOperations(
            BinaryFileReader binaryFileReader,
            BinaryFileWriter binaryFileWriter,
            XmlFileReader xmlFileReader,
            SettingsService settingsService
    ) {
        return new Editor(
                binaryFileReader, binaryFileWriter, xmlFileReader, settingsService
        );
    }
}
