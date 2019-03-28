package ru.editor.binaryeditor.core.services;

import java.io.File;
import lombok.RequiredArgsConstructor;
import org.simpleframework.xml.core.Persister;
import ru.editor.binaryeditor.core.domain.XmlFile;

@RequiredArgsConstructor
public class XmlFileReader {

  private final SettingsService settingsService;

  public XmlFile read() throws Exception {
    return new Persister().read(XmlFile.class, new File(settingsService.getXmlFilePath()));
  }
}
