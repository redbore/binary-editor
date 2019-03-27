package ru.editor.binaryeditor.core.services;

import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.editor.binaryeditor.core.domain.BinaryFile;

@Slf4j
@RequiredArgsConstructor
public class EditorOperations {

  private final BinaryFileReader binaryFileReader;
  private final BinaryFileWriter binaryFileWriter;

  private final SettingsService settingsService;

  public void test() throws Exception {
    BinaryFile read = binaryFileReader.read();
    binaryFileWriter.write(read);
    BinaryFile read1 = binaryFileReader.read();

    Arrays.equals(read.getBytes(), read1.getBytes());
  }
}
