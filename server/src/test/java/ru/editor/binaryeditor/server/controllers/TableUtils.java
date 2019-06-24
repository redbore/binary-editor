package ru.editor.binaryeditor.server.controllers;

import rest.Field;
import rest.TableDescription;

import java.util.List;

public final class TableUtils {

    public static TableDescription selectTable(List<TableDescription> tableDescriptions, String tableName) {
        return tableDescriptions.stream()
                .filter(tableDescription -> tableDescription.getName().equals(tableName))
                .findFirst().get();
    }

    public static Field selectField(
            List<Field> fields, List<String> columnNames, String selectedColumnName, Long rowNumber
    ) {
        int columnIndex = columnNames.indexOf(selectedColumnName);
        return fields.get(columnIndex * (rowNumber.intValue() - 1));
    }
}
