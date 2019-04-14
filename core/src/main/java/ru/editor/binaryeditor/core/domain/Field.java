package ru.editor.binaryeditor.core.domain;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Field {

  @NonNull
  private final UUID uuid;

  @NonNull
  private final String name;

  @NonNull
  private Object value;
}
