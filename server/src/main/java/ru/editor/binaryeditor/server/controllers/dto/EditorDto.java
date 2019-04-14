package ru.editor.binaryeditor.server.controllers.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EditorDto {

  private final List<TableNameDto> tablesNames;

  private final TableDto selectedTable;

}
