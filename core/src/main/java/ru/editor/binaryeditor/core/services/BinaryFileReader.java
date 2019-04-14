package ru.editor.binaryeditor.core.services;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import ru.editor.binaryeditor.core.domain.BinaryFile;
import ru.editor.binaryeditor.core.domain.Field;
import ru.editor.binaryeditor.core.domain.Instance;
import ru.editor.binaryeditor.core.domain.Paths;
import ru.editor.binaryeditor.core.domain.Type;
import ru.editor.binaryeditor.core.domain.XmlField;
import ru.editor.binaryeditor.core.domain.XmlFile;
import ru.editor.binaryeditor.core.domain.XmlSegment;

@RequiredArgsConstructor
public class BinaryFileReader {

  private byte[] bytes;
  private AtomicInteger offset;
  /**
   * Field necessary to obtain Instance count
   */
  private List<Type> tempTypes;

  public BinaryFile read(Paths paths, XmlFile xmlFile) throws Exception {
    bytes = Files.readAllBytes(new File(paths.getBinary()).toPath());
    offset = new AtomicInteger();
    tempTypes = new ArrayList<>();

    BinaryFile binaryFile = BinaryFile.builder()
        .uuid(UUID.randomUUID())
        .types(readTypes(xmlFile.getXmlSegments()))
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
    Type type = xmlSegment.getCount() == null || xmlSegment.getCount().equals("")
        ? Type.builder()
        .uuid(UUID.randomUUID())
        .name(xmlSegment.getName())
        .instances(Collections.singletonList(readInstance(xmlSegment)))
        .build()

        : Type.builder()
            .uuid(UUID.randomUUID())
            .name(xmlSegment.getName())
            .instances(readInstances(xmlSegment))
            .build();
    tempTypes.add(type);
    return type;
  }

  private Instance readInstance(XmlSegment xmlSegment) {
    return Instance.builder()
        .uuid(UUID.randomUUID())
        .fields(readFields(xmlSegment.getXmlFields()))
        .build();
  }

  private List<Instance> readInstances(XmlSegment xmlSegment) {
    Integer count = getInstanceCount(tempTypes, xmlSegment.getCount());
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
    Field field;
    switch (xmlField.getType()) {
      case "int":
        field = Field.builder()
            .uuid(UUID.randomUUID())
            .name(xmlField.getName())
            .value(readInt(bytes, offset.get()))
            .build();
        break;
      case "float":
        field = Field.builder()
            .uuid(UUID.randomUUID())
            .name(xmlField.getName())
            .value(readFloat(bytes, offset.get()))
            .build();
        break;
      case "string":
        field = Field.builder()
            .uuid(UUID.randomUUID())
            .name(xmlField.getName())
            .value(readString(bytes, offset.get(), xmlField.getLength()))
            .build();
        break;
      default:
        throw new RuntimeException();
    }
    offset.addAndGet(xmlField.getLength());
    return field;
  }

  private Integer readInt(byte[] bytes, Integer offset) {
    return ByteBuffer.wrap(bytes, offset, Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).getInt();
  }

  private Float readFloat(byte[] bytes, Integer offset) {
    return ByteBuffer.wrap(bytes, offset, Integer.BYTES).order(ByteOrder.LITTLE_ENDIAN).getFloat();
  }

  private String readString(byte[] bytes, Integer offset, Integer length) {
    return StandardCharsets.UTF_8.decode(ByteBuffer.wrap(bytes, offset, length)).toString().trim();
  }

  private static Integer getInstanceCount(List<Type> tempTypes, String count) {
    if (tempTypes.isEmpty()) {
      throw new RuntimeException();
    }
    String[] names = count.split("[.]");
    return tempTypes
        .stream()
        .filter(tempType -> tempType.getName().equals(names[0]))
        .findFirst()
        .map(Type::getInstances)
        .flatMap(instances -> instances
            .stream()
            .findFirst()
            .flatMap(instance -> instance.getFields()
                .stream()
                .filter(field -> field.getName().equals(names[1]))
                .findFirst()
                .map(field -> (Integer) field.getValue()))).get();
  }
}
