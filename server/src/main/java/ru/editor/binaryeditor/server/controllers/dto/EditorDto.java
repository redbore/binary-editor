package ru.editor.binaryeditor.server.controllers.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class EditorDto {

    private final List<TableNameDto> tablesNames;

    private final TableDto selectedTable;

}
