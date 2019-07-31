package ru.editor.binaryeditor.server.controllers;

import rest.Field;
import rest.Row;
import rest.TableDescription;

import java.util.List;
import java.util.stream.Collectors;

public final class TableUtils {

    public static TableDescription selectTable(List<TableDescription> tableDescriptions, String tableName) {
        return tableDescriptions.stream()
                .filter(tableDescription -> tableDescription.getName().equals(tableName))
                .findFirst().get();
    }

    public static Field selectField(
            List<Row> rows, List<String> columnNames, String selectedColumnName, Long rowNumber
    ) {
        List<Field> fields = rows.stream()
                .map(Row::getFields)
                .flatMap(List::stream)
                .collect(Collectors.toList());
        int columnIndex = columnNames.indexOf(selectedColumnName);
        return fields.get(columnIndex * (rowNumber.intValue() - 1));
    }
}
