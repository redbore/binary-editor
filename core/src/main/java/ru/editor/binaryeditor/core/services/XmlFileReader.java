package ru.editor.binaryeditor.core.services;

import org.simpleframework.xml.core.Persister;
import ru.editor.binaryeditor.core.domain.XmlFile;

import java.io.File;

public class XmlFileReader {

    public XmlFile read(String path) throws Exception {
        return new Persister().read(XmlFile.class, new File(path));
    }
}
