package ru.editor.binaryeditor.core.domain;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class Field {

  @NonNull
  private UUID uuid;

  @NonNull
  private String name;

  @NonNull
  private Object value;
}
