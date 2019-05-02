package ru.editor.binaryeditor.core.services;

import lombok.RequiredArgsConstructor;
import org.simpleframework.xml.core.Persister;
import ru.editor.binaryeditor.core.domain.XmlFile;
import ru.editor.binaryeditor.core.services.type.FieldHandlerFactory;

import java.io.File;

@RequiredArgsConstructor
public class XmlFileReader {

    private final FieldHandlerFactory fieldHandlerFactory;

    public XmlFile read(String path) throws Exception {
        XmlFile xmlFile = new Persister().read(XmlFile.class, new File(path));
        initFieldLength(xmlFile);
        return xmlFile;
    }

    private void initFieldLength(XmlFile xmlFile) {
        xmlFile.xmlSegments()
                .forEach(xmlSegment -> xmlSegment.xmlFields()
                        .forEach(xmlField -> {
                                    if (!xmlField.type().equals("string")) {
                                        xmlField.length(fieldHandlerFactory.length(xmlField.type()));
                                    }
                                }
                        ));
    }
}
