package ru.editor.binaryeditor.server.controllers;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.editor.binaryeditor.core.domain.BinaryFile;
import ru.editor.binaryeditor.core.domain.Field;
import ru.editor.binaryeditor.core.domain.Instance;
import ru.editor.binaryeditor.core.domain.Paths;
import ru.editor.binaryeditor.core.domain.Type;
import ru.editor.binaryeditor.server.controllers.dto.EditorDto;
import ru.editor.binaryeditor.server.controllers.dto.FieldDto;
import ru.editor.binaryeditor.server.controllers.dto.PathsDto;
import ru.editor.binaryeditor.server.controllers.dto.RowDto;
import ru.editor.binaryeditor.server.controllers.dto.TableDto;
import ru.editor.binaryeditor.server.controllers.dto.TableNameDto;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Mapper {

  public static EditorDto toEditorDto(BinaryFile binaryFile, UUID selectedTable) {
    if (binaryFile == null) {
      throw new RuntimeException("Binary file not found");
    }
    return EditorDto.builder()
        .tablesNames(toTablesNames(binaryFile.getTypes()))
        .selectedTable(selectedTable == null
            ? null
            : toTableDto(binaryFile.findType(selectedTable).getInstances(), selectedTable))
        .build();
  }

  public static PathsDto toPathsDto(Paths paths) {
    return PathsDto.builder()
        .xml(paths.getXml())
        .binary(paths.getBinary())
        .build();
  }

  public static Paths toPaths(PathsDto paths) {
    return Paths.builder()
        .xml(paths.getXml())
        .binary(paths.getBinary())
        .build();
  }

  private static TableDto toTableDto(List<Instance> instances, UUID tableId) {
    return instances.isEmpty()
        ? null
        : TableDto.builder()
            .uuid(tableId)
            .columnsNames(instances.get(0).getFields().stream()
                .map(Field::getName)
                .collect(Collectors.toList()))
            .rows(instances.stream()
                .map(Mapper::toRowDto)
                .collect(Collectors.toList()))
            .build();
  }

  private static RowDto toRowDto(Instance instance) {
    return RowDto.builder()
        .uuid(instance.getUuid())
        .fields(instance.getFields().stream()
            .map(Mapper::toFieldDto)
            .collect(Collectors.toList()))
        .uuid(instance.getUuid())
        .build();
  }

  private static FieldDto toFieldDto(Field field) {
    return FieldDto.builder()
        .name(field.getName())
        .uuid(field.getUuid())
        .value(field.getValue())
        .build();
  }

  private static List<TableNameDto> toTablesNames(List<Type> types) {
    return types.stream()
        .map(type -> TableNameDto.builder()
            .name(type.getName())
            .uuid(type.getUuid())
            .build())
        .collect(Collectors.toList());
  }
}
