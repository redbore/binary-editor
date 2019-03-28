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
    return new Paths(
        settingsService.getBinaryFilePath(),
        settingsService.getXmlFilePath()
    );
  }

  public Paths createPaths(Paths paths) {
    return new Paths(
        settingsService.saveBinaryFilePath(paths.getBinary()),
        settingsService.saveXmlFilePath(paths.getXml())
    );
  }

  public void suicide() {
    System.exit(0);
  }
}
