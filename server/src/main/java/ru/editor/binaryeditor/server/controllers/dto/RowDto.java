package ru.editor.binaryeditor.server.controllers.dto;

import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RowDto {

  private final UUID uuid;

  private final List<FieldDto> fieldsViews;
}
