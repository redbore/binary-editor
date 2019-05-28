package ru.editor.binaryeditor.core.services;

import lombok.RequiredArgsConstructor;
import ru.editor.binaryeditor.core.domain.*;
import ru.editor.binaryeditor.core.services.type.FieldHandlerFactory;
import ru.editor.binaryeditor.core.services.type.FieldReader;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
public class BinaryReader {

    private final FieldHandlerFactory fieldHandlerFactory;
    private final CachedFileService fileService;

    private byte[] bytes;
    private AtomicInteger offset;

    private OpenedBinary openedBinary;

    /**
     * Field necessary to obtain Instance count
     */
    public OpenedBinary read(Specification specification) throws Exception {

        bytes = fileService.getBinaryFile();
        offset = new AtomicInteger();

        OpenedBinary openedBinary = OpenedBinary.builder()
                .types(new ArrayList<>())
                .uuid(UUID.randomUUID())
                .build();

        this.openedBinary = openedBinary;

        specification.xmlSegments()
                .forEach(xmlSegment -> readType(xmlSegment, openedBinary.types()));

        this.openedBinary = null;
        bytes = null;
        offset = null;

        return openedBinary;
    }

    private void readType(XmlSegment xmlSegment, List<Type> types) {
        ArrayList<Instance> instances = new ArrayList<>();

        Type type = Type.builder()
                .uuid(UUID.randomUUID())
                .name(xmlSegment.name())
                .instances(instances)
                .build();
        types.add(type);

        Integer count = xmlSegment.count(openedBinary);

        for (int i = 0; i < count; i++) {
            readInstance(xmlSegment, instances);
        }
    }

    private void readInstance(XmlSegment xmlSegment, List<Instance> instances) {
        ArrayList<Field> fields = new ArrayList<>();

        Instance instance = Instance.builder()
                .uuid(UUID.randomUUID())
                .fields(fields)
                .build();
        instances.add(instance);

        xmlSegment.xmlFields()
                .forEach(xmlField -> readField(xmlField, fields));
    }

    private void readField(XmlField xmlField, List<Field> fields) {
        Field field = Field.builder()
                .uuid(UUID.randomUUID())
                .name(xmlField.name())
                .value(readValue(xmlField))
                .build();
        fields.add(field);
    }

    private Object readValue(XmlField xmlField) {
        FieldReader reader = fieldHandlerFactory.reader(xmlField.type());
        return reader.read(bytes, offset, xmlField.length(openedBinary, fieldHandlerFactory));
    }
}
