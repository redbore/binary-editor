package ru.editor.binaryeditor.server.controllers.dto;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FieldDto {

  private final UUID uuid;

  private final String name;

  private final Object value;

}
