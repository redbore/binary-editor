package ru.editor.binaryeditor.core.services;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import ru.editor.binaryeditor.core.domain.BinaryFile;
import ru.editor.binaryeditor.core.domain.Field;
import ru.editor.binaryeditor.core.domain.Paths;
import ru.editor.binaryeditor.core.domain.XmlFile;

@RequiredArgsConstructor
public class Editor {

  private final BinaryFileReader binaryFileReader;
  private final BinaryFileWriter binaryFileWriter;
  private final XmlFileReader xmlFileReader;
  private final SettingsService settingsService;

  private UUID selectedTable;
  private BinaryFile binaryFile;

  public void selectTable(UUID tableId) {
    selectedTable = tableId;
  }

  public void openFile(Paths paths) throws Exception {
    XmlFile xmlFile = xmlFileReader.read(paths.getXml());
    binaryFile = binaryFileReader.read(paths, xmlFile);
    settingsService.savePaths(paths);
    selectedTable = null;
  }

  public void saveFile(Paths paths) throws Exception {
    binaryFileWriter.write(binaryFile, paths);
    openFile(paths);
  }

  public void editField(UUID typeId, UUID instanceId, UUID fieldId, Object value) {
    Field field = binaryFile
        .findType(typeId)
        .findInstance(instanceId)
        .findField(fieldId);
    field.setValue(value);
  }

  public BinaryFile getBinaryFile() {
    return binaryFile;
  }

  public Paths getPaths() {
    return settingsService.getPaths();
  }

  public UUID getSelectedTable() {
    return selectedTable;
  }
}
