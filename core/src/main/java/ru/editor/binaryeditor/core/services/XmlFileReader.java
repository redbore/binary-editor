package ru.editor.binaryeditor.core.services;

import java.io.File;
import org.simpleframework.xml.core.Persister;
import ru.editor.binaryeditor.core.domain.XmlFile;

public class XmlFileReader {

  public XmlFile read(String path) throws Exception {
    return new Persister().read(XmlFile.class, new File(path));
  }
}
