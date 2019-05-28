package ru.editor.binaryeditor.server.controllers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.editor.binaryeditor.core.domain.Field;
import ru.editor.binaryeditor.core.domain.Instance;
import ru.editor.binaryeditor.core.domain.OpenedBinary;
import ru.editor.binaryeditor.core.domain.Type;
import ru.editor.binaryeditor.server.controllers.dto.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Mapper {

  public static EditorDto toEditorDto(OpenedBinary openedBinary, UUID selectedTable) {
    if (openedBinary == null) {
      throw new RuntimeException("Binary file not found");
    }
    return EditorDto.builder()
            .tablesNames(toTablesNames(openedBinary.types()))
            .selectedTable(selectedTable == null
                    ? null
                    : toTableDto(openedBinary.type(selectedTable).instances(), selectedTable))
            .build();
  }

  private static TableDto toTableDto(List<Instance> instances, UUID tableId) {
    return instances.isEmpty()
            ? null
            : TableDto.builder()
            .uuid(tableId)
            .columnsNames(instances.get(0).fields().stream()
                    .map(Field::name)
                    .collect(Collectors.toList()))
            .rows(instances.stream()
                    .map(Mapper::toRowDto)
                    .collect(Collectors.toList()))
            .build();
  }

  private static RowDto toRowDto(Instance instance) {
    return RowDto.builder()
            .uuid(instance.uuid())
            .fields(instance.fields().stream()
                    .map(Mapper::toFieldDto)
                    .collect(Collectors.toList()))
            .uuid(instance.uuid())
            .build();
  }

  private static FieldDto toFieldDto(Field field) {
    return FieldDto.builder()
            .name(field.name())
            .uuid(field.uuid())
            .value(field.value())
            .build();
  }

  private static List<TableNameDto> toTablesNames(List<Type> types) {
    return types.stream()
            .map(type -> TableNameDto.builder()
                    .name(type.name())
                    .uuid(type.uuid())
                    .build())
            .collect(Collectors.toList());
  }
}
