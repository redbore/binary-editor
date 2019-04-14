package ru.editor.binaryeditor.core.domain;

import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class Instance {

  @NonNull
  private final UUID uuid;

  @NonNull
  private final List<Field> fields;


  public Field findField(UUID fieldId) {
    return fields.stream()
        .filter(field -> field.getUuid().equals(fieldId))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Field not found"));
  }
}
