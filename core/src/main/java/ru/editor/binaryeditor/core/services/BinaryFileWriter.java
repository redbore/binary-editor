package ru.editor.binaryeditor.core.services;

import lombok.RequiredArgsConstructor;
import ru.editor.binaryeditor.core.domain.*;
import ru.editor.binaryeditor.core.services.type.TypeHandlerFactory;
import ru.editor.binaryeditor.core.services.type.TypeWriter;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.file.Files;

@RequiredArgsConstructor
public class BinaryFileWriter {

    private final XmlFileReader xmlFileReader;
    private final TypeHandlerFactory typeHandlerFactory;

    private ByteBuffer buffer;

    public void write(BinaryFile binaryFile, Paths paths) throws Exception {
        XmlFile xmlFile = xmlFileReader.read(paths.xml());
        buffer = ByteBuffer.allocate(binaryFile.getSize(xmlFile));

        binaryFile.types()
                .forEach(type -> writeType(type, xmlFile.findXmlSegment(type.name())));

        Files.write(new File(paths.binary()).toPath(), buffer.array());

        buffer = null;
    }

    private void writeType(Type type, XmlSegment xmlSegment) {
        type.instances()
                .forEach(instance -> writeInstance(instance, xmlSegment));
    }

    private void writeInstance(Instance instance, XmlSegment xmlSegment) {
        instance.fields()
                .forEach(field -> writeField(field, xmlSegment.findXmlField(field.name())));
    }

    private void writeField(Field field, XmlField xmlField) {
        TypeWriter writer = typeHandlerFactory.writer(xmlField.type());
        writer.write(buffer, field.value(), xmlField.length());
    }
}
