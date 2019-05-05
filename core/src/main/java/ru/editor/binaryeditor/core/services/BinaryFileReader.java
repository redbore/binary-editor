package ru.editor.binaryeditor.core.services;

import lombok.RequiredArgsConstructor;
import ru.editor.binaryeditor.core.domain.*;
import ru.editor.binaryeditor.core.services.type.FieldHandlerFactory;
import ru.editor.binaryeditor.core.services.type.FieldReader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static ru.editor.binaryeditor.core.domain.Field.field;

@RequiredArgsConstructor
public class BinaryFileReader {

    private final FieldHandlerFactory fieldHandlerFactory;
    private final XmlFileReader xmlFileReader;
    private final CachedFileService cachedFileService;

    private byte[] bytes;
    private AtomicInteger offset;
    /**
     * Field necessary to obtain Instance count
     */
    private List<Type> tempTypes;

    public BinaryFile read() throws Exception {
        Path binaryPath = cachedFileService.binaryPath();
        Path xmlPath = cachedFileService.xmlPath();
        XmlFile xmlFile = xmlFileReader.read(xmlPath);

        bytes = Files.readAllBytes(binaryPath);
        offset = new AtomicInteger();
        tempTypes = new ArrayList<>();

        BinaryFile binaryFile = BinaryFile.builder()
                .uuid(UUID.randomUUID())
                .types(readTypes(xmlFile.xmlSegments()))
                .build();

        bytes = null;
        offset = null;
        tempTypes = null;

        return binaryFile;
    }

    private List<Type> readTypes(List<XmlSegment> xmlSegments) {
        return xmlSegments.stream()
                .map(this::readType)
                .collect(Collectors.toList());
    }

    private Type readType(XmlSegment xmlSegment) {
        Type type = xmlSegment.count() == null || xmlSegment.count().equals("")
                ? Type.builder()
                .uuid(UUID.randomUUID())
                .name(xmlSegment.name())
                .instances(Collections.singletonList(readInstance(xmlSegment)))
                .build()

                : Type.builder()
                .uuid(UUID.randomUUID())
                .name(xmlSegment.name())
                .instances(readInstances(xmlSegment))
                .build();
        tempTypes.add(type);
        return type;
    }

    private Instance readInstance(XmlSegment xmlSegment) {
        return Instance.builder()
                .uuid(UUID.randomUUID())
                .fields(readFields(xmlSegment.xmlFields()))
                .build();
    }

    private List<Instance> readInstances(XmlSegment xmlSegment) {
        Integer count = getInstanceCount(tempTypes, xmlSegment.count());
        return IntStream.range(0, count)
                .mapToObj(i -> readInstance(xmlSegment))
                .collect(Collectors.toList());
    }

    private List<Field> readFields(List<XmlField> xmlFields) {
        return xmlFields.stream()
                .map(this::readField)
                .collect(Collectors.toList());
    }

    private Field readField(XmlField xmlField) {
        FieldReader reader = fieldHandlerFactory.reader(xmlField.type());
        Object value = reader.read(bytes, offset, xmlField.length());
        return field(xmlField.name(), value);
    }

    private static Integer getInstanceCount(List<Type> tempTypes, String count) {
        if (tempTypes.isEmpty()) {
            throw new RuntimeException();
        }
        String[] names = count.split("[.]");
        return tempTypes
                .stream()
                .filter(tempType -> tempType.name().equals(names[0]))
                .findFirst()
                .map(Type::instances)
                .flatMap(instances -> instances
                        .stream()
                        .findFirst()
                        .flatMap(instance -> instance.fields()
                                .stream()
                                .filter(field -> field.name().equals(names[1]))
                                .findFirst()
                                .map(field -> (Integer) field.value()))).get();
    }
}
