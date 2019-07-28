package ru.editor.binaryeditor.server.controllers;

import java.io.IOException;

public interface EditorOperationsTest {

    void testOpenFile() throws IOException;

    void testReplaceOneFile() throws IOException;

    void testViewFile() throws IOException;

    void testPagination(String tableName, Long limit, Long offset, Long expectedRowCount) throws IOException;

    void testFieldEdit(
            String tableName, Long rowCount, Long pageNumber, String columnName, Long rowNumber, String newValue
    ) throws IOException;

    void testSaveFile() throws IOException;
}
