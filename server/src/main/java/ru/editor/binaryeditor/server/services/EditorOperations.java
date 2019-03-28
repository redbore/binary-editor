package ru.editor.binaryeditor.server.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.editor.binaryeditor.core.domain.BinaryFile;
import ru.editor.binaryeditor.core.services.BinaryFileReader;
import ru.editor.binaryeditor.core.services.BinaryFileWriter;
import ru.editor.binaryeditor.core.services.SettingsService;
import ru.editor.binaryeditor.server.controllers.Paths;

@Slf4j
@RequiredArgsConstructor
public class EditorOperations {

  private final BinaryFileReader binaryFileReader;
  private final BinaryFileWriter binaryFileWriter;
  private final SettingsService settingsService;

  public BinaryFile getBinaryFile() throws Exception {
    return binaryFileReader.read();
  }

  public BinaryFile createBinaryFile(BinaryFile binaryFile) throws Exception {
    return binaryFileWriter.write(binaryFile);
  }

  public Paths getPaths() {
    return Paths.builder()
        .binary(settingsService.getBinaryFilePath())
        .xml(settingsService.getXmlFilePath())
        .build();
  }

  public Paths createPaths(Paths paths) {
    return Paths.builder()
        .binary(settingsService.saveBinaryFilePath(paths.getBinary()))
        .xml(settingsService.saveXmlFilePath(paths.getXml()))
        .build();
  }

  public void suicide() {
    System.exit(0);
  }
}
