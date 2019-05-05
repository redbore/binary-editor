package ru.editor.binaryeditor.core.services;

import lombok.RequiredArgsConstructor;
import ru.editor.binaryeditor.core.domain.*;
import ru.editor.binaryeditor.core.services.type.FieldHandlerFactory;
import ru.editor.binaryeditor.core.services.type.TypeConverter;

import java.nio.ByteBuffer;
import java.nio.file.Path;

@RequiredArgsConstructor
public class BinaryWriter {

    private final FieldHandlerFactory fieldHandlerFactory;
    private final SpecificationReader specificationReader;
    private final CachedFileService fileService;

    private ByteBuffer buffer;

    public EditorFile write(OpenedBinary openedOpenedBinary) throws Exception {
        Specification specification = specificationReader.read();

        buffer = ByteBuffer.allocate(openedOpenedBinary.getSize(specification));

        openedOpenedBinary.types()
                .forEach(type -> writeType(type, specification.getXmlSegment(type.name())));

        byte[] body = buffer.array();
        Path binaryPath = fileService.updateBinary(body);

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
