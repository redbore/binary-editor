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
  private UUID uuid;

  @NonNull
  private List<Field> fields;
}
