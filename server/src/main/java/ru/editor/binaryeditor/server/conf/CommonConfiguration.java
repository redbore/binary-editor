package ru.editor.binaryeditor.server.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.editor.binaryeditor.core.dao.*;
import ru.editor.binaryeditor.core.services.BinaryFileReader;
import ru.editor.binaryeditor.core.services.BinaryFileWriter;
import ru.editor.binaryeditor.core.services.Editor;
import ru.editor.binaryeditor.core.services.SpecificationReader;
import ru.editor.binaryeditor.core.services.interfaces.FileReader;
import ru.editor.binaryeditor.core.services.interfaces.FileWriter;

@Configuration
public class CommonConfiguration {

    @Bean
    public Editor editor(
            SpecificationDao specificationDao,
            BinaryFileDao binaryFileDao,
            SegmentDao segmentDao,
            FileReader binaryFileReader,
            FileReader specificationReader,
            FieldDescriptionDao fieldDescriptionDao,
            FieldDao fieldDao,
            FileWriter binaryFileWriter
    ) {
        return new Editor(
                specificationDao,
                binaryFileDao,
                segmentDao,
                binaryFileReader,
                specificationReader,
                fieldDescriptionDao,
                fieldDao,
                binaryFileWriter
        );
    }

    @Bean
    public FileReader binaryFileReader(
            FieldDao fieldDao,
            SegmentDao segmentDao,
            BinaryFileDao binaryFileDao,
            FieldDescriptionDao fieldDescriptionDao
    ) {
        return new BinaryFileReader(fieldDao, segmentDao, binaryFileDao, fieldDescriptionDao);
    }

    @Bean
    public FileReader specificationReader(
            SpecificationDao specificationDao,
            SegmentDao segmentDao,
            FieldDescriptionDao fieldDescriptionDao
    ) {
        return new SpecificationReader(specificationDao, segmentDao, fieldDescriptionDao);
    }

    @Bean
    public FileWriter binaryFileWriter(
            BinaryFileDao binaryFileDao,
            FieldDescriptionDao fieldDescriptionDao,
            FieldDao fieldDao
    ) {
        return new BinaryFileWriter(fieldDao, binaryFileDao, fieldDescriptionDao);
    }

}
