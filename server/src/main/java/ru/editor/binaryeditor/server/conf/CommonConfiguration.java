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
    public CachedFileService cachedFileService() {
        return new CachedFileServiceImpl();
    }

    @Bean
    public SpecificationReader specificationReader(CachedFileService cachedFileService) {
        return new SpecificationReader(cachedFileService);
    }

    @Bean
    public BinaryReader binaryReader(
            FieldHandlerFactory fieldHandlerFactory,
            CachedFileService cachedFileService
    ) {
        return new BinaryReader(fieldHandlerFactory, cachedFileService);
    }

    @Bean
    public BinaryWriter binaryWriter(
            FieldHandlerFactory fieldHandlerFactory,
            CachedFileService cachedFileService
    ) {
        return new BinaryWriter(fieldHandlerFactory, cachedFileService);
    }

    @Bean(initMethod = "init")
    public Editor editor(
            BinaryReader binaryReader,
            BinaryWriter binaryWriter,
            SpecificationReader specificationReader,
            CachedFileService cachedFileService
    ) {
        return new Editor(binaryReader, binaryWriter, cachedFileService, specificationReader);
    }
}
