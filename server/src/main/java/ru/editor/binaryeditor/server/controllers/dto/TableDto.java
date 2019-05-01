package ru.editor.binaryeditor.server.controllers.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class TableDto {

    private final UUID uuid;

    private final List<String> columnsNames;

    private final List<RowDto> rows;
}
