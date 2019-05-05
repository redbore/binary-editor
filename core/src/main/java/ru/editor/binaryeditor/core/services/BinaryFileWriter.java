package ru.editor.binaryeditor.core.services;

import lombok.RequiredArgsConstructor;
import ru.editor.binaryeditor.core.domain.*;
import ru.editor.binaryeditor.core.services.type.FieldHandlerFactory;
import ru.editor.binaryeditor.core.services.type.TypeConverter;

import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;

@RequiredArgsConstructor
public class BinaryFileWriter {

    private final FieldHandlerFactory fieldHandlerFactory;
    private final XmlFileReader xmlFileReader;
    private final CachedFileService cachedFileService;

    private ByteBuffer buffer;

    public EditorFile write(BinaryFile openedBinaryFile) throws Exception {
        Path xmlPath = cachedFileService.xmlPath();
        Path binaryPath = cachedFileService.binaryPath();

        XmlFile xmlFile = xmlFileReader.read(xmlPath);
        buffer = ByteBuffer.allocate(openedBinaryFile.getSize(xmlFile));

        openedBinaryFile.types()
                .forEach(type -> writeType(type, xmlFile.getXmlSegment(type.name())));

        byte[] body = buffer.array();
        Files.write(binaryPath, body);

        buffer = null;
        return EditorFile.builder()
                .body(TypeConverter.toIntArray(body))
                .name(binaryPath.getFileName().toString())
                .build();
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
        fieldHandlerFactory.writer(xmlField.type()).write(buffer, field.value(), xmlField.length());
    }
}
