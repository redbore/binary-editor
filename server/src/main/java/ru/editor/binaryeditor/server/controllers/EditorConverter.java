package ru.editor.binaryeditor.server.controllers;

import rest.Field;
import rest.View;
import rest.*;
import ru.editor.binaryeditor.core.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;
import static rest.ApiConverter.toByteArray;
import static rest.ApiConverter.toIntArray;

public final class EditorConverter {

    public static BinaryFile toDomainBinaryFile(File file) {
        return BinaryFile.builder()
                .id(file.getId())
                .name(file.getName())
                .body(file.getBody() != null ? toByteArray(file.getBody()) : null)
                .build();
    }

    public static Specification toDomainSpecification(File file) {
        return Specification.builder()
                .id(file.getId())
                .name(file.getName())
                .body(file.getBody() != null ? toByteArray(file.getBody()) : null)
                .build();
    }

    public static View toView(ru.editor.binaryeditor.core.domain.View view) {
        List<TableDescription> tableDescriptions = view.segments().entrySet().stream()
                .map((entry) -> {
                    Segment segment = entry.getKey();
                    SegmentInfo segmentInfo = entry.getValue();
                    List<FieldDescription> fieldDescriptions = segmentInfo.fieldDescriptions();

                    return TableDescription.builder()
                            .id(segment.id())
                            .name(segment.name())
                            .columnsNames(fieldDescriptions.stream()
                                    .map(FieldDescription::name)
                                    .collect(Collectors.toList()))
                            .rowCount(segmentInfo.fieldCount() / fieldDescriptions.size())
                            .build();
                })
                .collect(Collectors.toList());
        return View.builder()
                .binaryFile(File.builder()
                        .id(view.binaryFile().id())
                        .name(view.binaryFile().name())
                        .build())
                .specification(File.builder()
                        .id(view.specification().id())
                        .name(view.specification().name())
                        .build())
                .tablesDescriptions(tableDescriptions)
                .build();
    }

    public static File toFile(BinaryFile binaryFile) {
        return File.builder()
                .name(binaryFile.name())
                .body(toIntArray(binaryFile.body()))
                .build();
    }

    public static List<Row> toRows(List<ru.editor.binaryeditor.core.domain.Field> fields, Integer columnCount) {
        // TODO: find another decision grouping by column count
        List<Row> rows = new ArrayList<>();
        Row tempRow = Row.builder()
                .fields(newArrayList())
                .build();

        for (int i = 0; i < fields.size(); i++) {
            if (i % columnCount == 0) {
                tempRow = Row.builder()
                        .fields(newArrayList())
                        .build();
                rows.add(tempRow);
            }
            ru.editor.binaryeditor.core.domain.Field field = fields.get(i);
            tempRow.getFields().add(Field.builder()
                    .id(field.id())
                    .value(field.value())
                    .build());
        }
        return rows;
    }
}
