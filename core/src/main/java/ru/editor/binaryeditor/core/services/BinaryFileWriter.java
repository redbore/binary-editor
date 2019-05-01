package ru.editor.binaryeditor.core.services;

import lombok.RequiredArgsConstructor;
import ru.editor.binaryeditor.core.domain.*;
import ru.editor.binaryeditor.core.services.type.FieldHandlerFactory;
import ru.editor.binaryeditor.core.services.type.FieldWriter;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.file.Files;

@RequiredArgsConstructor
public class BinaryFileWriter {

    private final XmlFileReader xmlFileReader;
    private final FieldHandlerFactory fieldHandlerFactory;

    private ByteBuffer buffer;

    public void write(BinaryFile binaryFile, Paths paths) throws Exception {
        XmlFile xmlFile = xmlFileReader.read(paths.xml());
        buffer = ByteBuffer.allocate(binaryFile.getSize(xmlFile));

        binaryFile.types()
                .forEach(type -> writeType(type, xmlFile.getXmlSegment(type.name())));

        Files.write(new File(paths.binary()).toPath(), buffer.array());

        buffer = null;
    }

    private void writeType(Type type, XmlSegment xmlSegment) {
        type.instances()
                .forEach(instance -> writeInstance(instance, xmlSegment));
    }

    private void writeInstance(Instance instance, XmlSegment xmlSegment) {
        instance.fields()
                .forEach(field -> writeField(field, xmlSegment.getXmlField(field.name())));
    }

    private void writeField(Field field, XmlField xmlField) {
        FieldWriter writer = fieldHandlerFactory.writer(xmlField.type());
        writer.write(buffer, field.value(), xmlField.length());
    }
}
