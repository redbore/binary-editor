package ru.editor.binaryeditor.server.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.editor.binaryeditor.core.domain.BinaryFile;
import ru.editor.binaryeditor.core.domain.Paths;
import ru.editor.binaryeditor.core.services.BinaryFileReader;
import ru.editor.binaryeditor.core.services.BinaryFileWriter;
import ru.editor.binaryeditor.server.controllers.SaveBinaryFile;

@Slf4j
@RequiredArgsConstructor
public class EditorOperations {

  private final BinaryFileReader binaryFileReader;
  private final BinaryFileWriter binaryFileWriter;
  private final SettingsService settingsService;

  public BinaryFile openBinaryFile(Paths paths) throws Exception {
    return binaryFileReader.read(paths);
  }

  public BinaryFile saveBinaryFile(SaveBinaryFile saveFile) throws Exception {
    BinaryFile write = binaryFileWriter.write(saveFile.getBinaryFile(), saveFile.getPaths());
    settingsService.savePaths(saveFile.getPaths());
    return write;
  }

  public Paths getPaths() {
    return settingsService.getPaths();
  }

  public void suicide() {
    System.exit(0);
  }
}
