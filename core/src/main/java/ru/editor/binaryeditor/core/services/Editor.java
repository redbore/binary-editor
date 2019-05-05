package ru.editor.binaryeditor.core.services;

import lombok.RequiredArgsConstructor;
import ru.editor.binaryeditor.core.domain.EditorFile;
import ru.editor.binaryeditor.core.domain.Field;
import ru.editor.binaryeditor.core.domain.OpenedBinary;

import java.nio.file.Path;
import java.util.UUID;

@RequiredArgsConstructor
public class Editor {

    private final BinaryReader binaryReader;
    private final BinaryWriter binaryWriter;
    private final CachedFileService fileService;

    private UUID selectedTable;
    private OpenedBinary openedBinary;

    private void init() throws Exception {
        openBinary();
    }

    public void selectTable(UUID tableId) {
        selectedTable = tableId;
    }

    public void openBinary() throws Exception {
        Path binary = fileService.binaryPath();
        Path specification = fileService.specificationPath();
        if (binary != null && specification != null) {
            openedBinary = binaryReader.read();
            selectedTable = null;
        }
    }

    public EditorFile saveBinary() throws Exception {
        return binaryWriter.write(openedBinary);
    }

    public void editField(UUID typeId, UUID instanceId, UUID fieldId, Object value) {
        Field field = openedBinary
                .getType(typeId)
                .getInstance(instanceId)
                .getField(fieldId);
        field.value(value);
    }

    public OpenedBinary view() {
        return openedBinary;
    }

    public UUID selectedTable() {
        return selectedTable;
    }
}
