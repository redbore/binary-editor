package ru.editor.binaryeditor.core.services;

import lombok.RequiredArgsConstructor;
import ru.editor.binaryeditor.core.domain.EditorFile;
import ru.editor.binaryeditor.core.domain.Field;
import ru.editor.binaryeditor.core.domain.OpenedBinary;
import ru.editor.binaryeditor.core.domain.Specification;

import java.nio.file.Path;
import java.util.UUID;

@RequiredArgsConstructor
public class Editor {

    private final BinaryReader binaryReader;
    private final BinaryWriter binaryWriter;
    private final CachedFileService fileService;
    private final SpecificationReader specificationReader;

    private UUID selectedTable;
    private OpenedBinary openedBinary;
    private Specification specification;

    private void init() throws Exception {
        openBinary();
    }

    public void selectTable(UUID tableId) {
        selectedTable = tableId;
    }

    public void openBinary() throws Exception {
        Path binaryPath = fileService.binaryPath();
        Path specificationPath = fileService.specificationPath();
        if (binaryPath != null && specificationPath != null) {
            try {
                specification = specificationReader.read();
                openedBinary = binaryReader.read(specification);
            } catch (Exception e) {
                e.printStackTrace();
            }
            selectedTable = null;
        }
    }

    public EditorFile saveBinary() throws Exception {
        return binaryWriter.write(openedBinary, specification);
    }

    public void editField(UUID typeId, UUID instanceId, UUID fieldId, Object value) {
        Field field = openedBinary
                .type(typeId)
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
