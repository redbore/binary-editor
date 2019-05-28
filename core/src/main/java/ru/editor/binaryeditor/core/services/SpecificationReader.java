package ru.editor.binaryeditor.core.services;

import lombok.RequiredArgsConstructor;
import org.simpleframework.xml.core.Persister;
import ru.editor.binaryeditor.core.domain.Specification;

import java.nio.file.Path;

@RequiredArgsConstructor
public class SpecificationReader {

    private final CachedFileService fileService;

    public Specification read() throws Exception {
        Path path = fileService.specificationPath();
        return new Persister().read(Specification.class, path.toFile());
    }
}
