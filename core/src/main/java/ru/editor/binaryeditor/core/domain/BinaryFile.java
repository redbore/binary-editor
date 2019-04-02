package ru.editor.binaryeditor.core.domain;

import java.util.List;
import java.util.OptionalInt;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class BinaryFile {

  @NonNull
  private UUID uuid;

  @NonNull
  private List<Type> types;

  public Integer getSize(XmlFile xmlFile) {
    return xmlFile.getXmlSegments().stream()
        .map(xmlSegment -> xmlSegment.getXmlFields().stream()
            .mapToInt(xmlField -> xmlField.getLength() * getInstanceSize(xmlSegment.getName()))
            .reduce((a, b) -> a + b))
        .mapToInt(OptionalInt::getAsInt)
        .reduce((a, b) -> a + b)
        .orElseThrow(RuntimeException::new);
  }

  private Integer getInstanceSize(String typeName) {
    return findType(typeName).getInstances().size();
  }

  private Type findType(String typeName) {
    return types.stream()
        .filter(type -> type.getName().equals(typeName))
        .findFirst()
        .orElseThrow(RuntimeException::new);
  }
}
