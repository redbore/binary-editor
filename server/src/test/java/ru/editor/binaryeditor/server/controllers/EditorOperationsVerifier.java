package ru.editor.binaryeditor.server.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import rest.*;

import java.io.IOException;
import java.util.List;

import static data.OpenFileData.openFile;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.editor.binaryeditor.server.controllers.TableUtils.selectField;
import static ru.editor.binaryeditor.server.controllers.TableUtils.selectTable;

@RequiredArgsConstructor
public class EditorOperationsVerifier {

    private final EditorClient editorClient;

    private Resource binaryFile;
    private Resource specification;

    public void verifyOpenFile() throws IOException {
        OpenFile openFile = openFile(binaryFile, specification);

        editorClient.open(openFile);
        View view = editorClient.view();

        assertThat(view.getSpecificationName()).isEqualTo(specification.getFilename());
        assertThat(view.getBinaryFileName()).isEqualTo(binaryFile.getFilename());
    }

    public void verifyView(List<TableDescription> expectedTableDescriptions) throws IOException {
        OpenFile openFile = openFile(binaryFile, specification);

        editorClient.open(openFile);
        View view = editorClient.view();

        assertThat(view.getSpecificationName()).isEqualTo(specification.getFilename());
        assertThat(view.getBinaryFileName()).isEqualTo(binaryFile.getFilename());
        assertThat(view.getTableDescriptions())
                .usingElementComparatorIgnoringFields("id")
                .isEqualTo(expectedTableDescriptions);
    }

    public void verifyPagination(
            String tableName, Long rowCount, Long pageNumber, Long expectedRowCount
    ) throws IOException {
        OpenFile openFile = openFile(binaryFile, specification);

        editorClient.open(openFile);
        View view = editorClient.view();

        TableDescription tableDescription = selectTable(view.getTableDescriptions(), tableName);
        List<Field> fields = editorClient.pagination(tableDescription.getId(), rowCount, pageNumber);
        assertThat(fields.size() / tableDescription.getColumnNames().size()).isEqualTo(expectedRowCount.intValue());
    }

    public void verifyFieldEdit(
            String tableName, Long rowCount, Long pageNumber, String columnName, Long rowNumber, String newValue
    ) throws IOException {
        OpenFile openFile = openFile(binaryFile, specification);

        editorClient.open(openFile);
        View view = editorClient.view();

        TableDescription tableDescription = selectTable(view.getTableDescriptions(), tableName);

        List<Field> fieldsBeforeEdit = editorClient.pagination(tableDescription.getId(), rowCount, pageNumber);
        Field fieldBeforeEdit = selectField(fieldsBeforeEdit, tableDescription.getColumnNames(), columnName, rowNumber);
        assertThat(fieldBeforeEdit.getValue()).isNotEqualTo(newValue);

        editorClient.fieldEdit(fieldBeforeEdit.getId(), newValue);

        List<Field> fieldsAfterEdit = editorClient.pagination(tableDescription.getId(), rowCount, pageNumber);
        Field fieldAfterEdit = selectField(fieldsAfterEdit, tableDescription.getColumnNames(), columnName, rowNumber);
        assertThat(fieldBeforeEdit.getId()).isEqualTo(fieldAfterEdit.getId());
        assertThat(fieldBeforeEdit.getValue()).isNotEqualTo(newValue);
    }

    public void verifySaveFile() throws IOException {
        OpenFile openFile = openFile(binaryFile, specification);

        editorClient.open(openFile);

        File fileBeforeEdit = editorClient.save();
        assertThat(fileBeforeEdit.getFileName()).isEqualTo(binaryFile.getFilename());
        assertThat(fileBeforeEdit.getFileBody()).isEqualTo(openFile.getBinaryFile().getFileBody());

        View view = editorClient.view();
        TableDescription tableDescription = selectTable(
                view.getTableDescriptions(),
                view.getTableDescriptions().get(0).getName()
        );

        List<Field> fieldsBeforeEdit = editorClient.pagination(tableDescription.getId(), 1L, 0L);
        Field fieldBeforeEdit = selectField(
                fieldsBeforeEdit,
                tableDescription.getColumnNames(),
                tableDescription.getColumnNames().get(0),
                1L
        );

        editorClient.fieldEdit(fieldBeforeEdit.getId(), "1");

        File fileAfterEdit = editorClient.save();
        assertThat(fileAfterEdit.getFileName()).isEqualTo(binaryFile.getFilename());
        assertThat(fileAfterEdit.getFileBody()).isNotEqualTo(openFile.getBinaryFile().getFileBody());
    }

    public void workFiles(Resource binaryFile, Resource specification) {
        this.binaryFile = binaryFile;
        this.specification = specification;
    }
}
