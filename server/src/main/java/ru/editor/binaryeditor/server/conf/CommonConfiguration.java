package ru.editor.binaryeditor.server.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.editor.binaryeditor.core.services.*;
import ru.editor.binaryeditor.core.services.type.FieldHandlerFactory;
import ru.editor.binaryeditor.server.services.CachedFileServiceImpl;

@Configuration
public class CommonConfiguration {

    @Bean(initMethod = "init")
    public FieldHandlerFactory fieldHandlerFactory() {
        return new FieldHandlerFactory();
    }

    @Bean
    public CachedFileService cashedService() {
        return new CachedFileServiceImpl();
    }

    @Bean
    public XmlFileReader xmlFileReader(FieldHandlerFactory fieldHandlerFactory) {
        return new XmlFileReader(fieldHandlerFactory);
    }

    @Bean
    public BinaryFileReader binaryFileReader(
            FieldHandlerFactory fieldHandlerFactory,
            XmlFileReader xmlFileReader,
            CachedFileService cachedFileService
    ) {
        return new BinaryFileReader(fieldHandlerFactory, xmlFileReader, cachedFileService);
    }

    @Bean
    public BinaryFileWriter binaryFileWriter(
            XmlFileReader xmlFileReader,
            FieldHandlerFactory fieldHandlerFactory,
            CachedFileService cachedFileService
    ) {
        return new BinaryFileWriter(fieldHandlerFactory, xmlFileReader, cachedFileService);
    }

    @Bean(initMethod = "init")
    public Editor editor(
            BinaryFileReader binaryFileReader,
            BinaryFileWriter binaryFileWriter,
            CachedFileService cachedFileService
    ) {
        return new Editor(
                binaryFileReader, binaryFileWriter, cachedFileService
        );
    }
}
