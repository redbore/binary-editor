package ru.editor.binaryeditor.core.services;

import lombok.RequiredArgsConstructor;
import ru.editor.binaryeditor.core.domain.BinaryFile;
import ru.editor.binaryeditor.core.domain.EditorFile;
import ru.editor.binaryeditor.core.domain.Field;

import java.nio.file.Path;
import java.util.UUID;

@RequiredArgsConstructor
public class Editor {

    private final BinaryFileReader binaryFileReader;
    private final BinaryFileWriter binaryFileWriter;
    private final CachedFileService cachedFileService;

    private UUID selectedTable;
    private BinaryFile openedBinaryFile;

    private void init() throws Exception {
        openBinaryFile();
    }

    public void selectTable(UUID tableId) {
        selectedTable = tableId;
    }

    public void openBinaryFile() throws Exception {
        Path binary = cachedFileService.binaryPath();
        Path xml = cachedFileService.xmlPath();
        if (binary != null && xml != null) {
            openedBinaryFile = binaryFileReader.read();
            selectedTable = null;
        }
    }

    public EditorFile saveBinaryFile() throws Exception {
        return binaryFileWriter.write(openedBinaryFile);
    }

    public void editField(UUID typeId, UUID instanceId, UUID fieldId, Object value) {
        Field field = openedBinaryFile
                .getType(typeId)
                .getInstance(instanceId)
                .getField(fieldId);
        field.value(value);
    }

    public BinaryFile view() {
        return openedBinaryFile;
    }

    public UUID selectedTable() {
        return selectedTable;
    }
}
