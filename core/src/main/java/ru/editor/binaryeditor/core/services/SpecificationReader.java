package ru.editor.binaryeditor.core.services;

import lombok.RequiredArgsConstructor;
import org.simpleframework.xml.core.Persister;
import ru.editor.binaryeditor.core.domain.Specification;
import ru.editor.binaryeditor.core.services.type.FieldHandlerFactory;

import java.nio.file.Path;

@RequiredArgsConstructor
public class SpecificationReader {

    private final FieldHandlerFactory fieldHandlerFactory;
    private final CachedFileService fileService;

    public Specification read() throws Exception {
        Path path = fileService.specificationPath();
        Specification specification = new Persister().read(Specification.class, path.toFile());
        initFieldLength(specification);
        return specification;
    }

    private void initFieldLength(Specification specification) {
        specification.xmlSegments()
                .forEach(xmlSegment -> xmlSegment.xmlFields()
                        .forEach(xmlField -> {
                                    if (!xmlField.type().equals("string")) {
                                        xmlField.length(fieldHandlerFactory.length(xmlField.type()));
                                    }
                                }
                        ));
    }
}
