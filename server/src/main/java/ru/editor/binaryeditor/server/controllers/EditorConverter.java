package ru.editor.binaryeditor.server.controllers;

import rest.Field;
import rest.File;
import rest.TableDescription;
import rest.View;
import ru.editor.binaryeditor.core.domain.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static rest.ApiConverter.toByteArray;
import static rest.ApiConverter.toIntArray;

public final class EditorConverter {

    public static BinaryFile toDomainBinaryFile(File file) {
        return BinaryFile.builder()
                .id(UUID.randomUUID())
                .name(file.getFileName())
                .body(toByteArray(file.getFileBody()))
                .build();
    }

    public static Specification toDomainSpecification(File file) {
        return Specification.builder()
                .id(UUID.randomUUID())
                .name(file.getFileName())
                .body(toByteArray(file.getFileBody()))
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
                            .columnNames(fieldDescriptions.stream()
                                    .map(FieldDescription::name)
                                    .collect(Collectors.toList()))
                            .rowCount(segmentInfo.fieldCount() / fieldDescriptions.size())
                            .build();
                })
                .collect(Collectors.toList());
        return View.builder()
                .binaryFileName(view.binaryFile().name())
                .specificationName(view.specification().name())
                .tableDescriptions(tableDescriptions)
                .build();
    }

    public static File toFile(BinaryFile binaryFile) {
        return File.builder()
                .fileName(binaryFile.name())
                .fileBody(toIntArray(binaryFile.body()))
                .build();
    }

    public static List<Field> toFields(List<ru.editor.binaryeditor.core.domain.Field> fields) {
        return fields.stream()
                .map(field -> Field.builder()
                        .id(field.id())
                        .value(field.value())
                        .build())
                .collect(Collectors.toList());
    }
}
