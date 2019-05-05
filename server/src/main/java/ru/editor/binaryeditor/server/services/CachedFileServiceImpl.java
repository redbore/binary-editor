package ru.editor.binaryeditor.server.services;

import ru.editor.binaryeditor.core.services.CachedFileService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class CachedFileServiceImpl implements CachedFileService {

    private static final String xml = "xml";
    private static final String binary = "binary";
    private static final String xmlPath = "cache\\" + xml;
    private static final String binaryPath = "cache\\" + binary;

    @Override
    public Path updateBinaryFile(String name, byte[] body) throws IOException {
        deleteBinary();
        Path filePath = new File(binaryPath + "/" + name).toPath();
        Files.createFile(filePath);
        Files.write(filePath, body);
        return filePath;
    }

    @Override
    public Path updateXmlFile(String name, byte[] body) throws IOException {
        deleteXml();
        Path filePath = new File(xmlPath + "/" + name).toPath();
        Files.createFile(filePath);
        Files.write(filePath, body);
        return filePath;
    }

    @Override
    public Path binaryPath() throws IOException {
        return findBinary().orElse(null);
    }

    @Override
    public Path xmlPath() throws IOException {
        return findXml().orElse(null);
    }

    private void deleteXml() throws IOException {
        Files
                .walk(new File(xmlPath).toPath())
                .filter(p -> !p.getFileName().toString().equals(xml))
                .forEach(this::deleteIfExists);
    }

    private void deleteBinary() throws IOException {
        Files
                .walk(new File(binaryPath).toPath())
                .filter(p -> !p.getFileName().toString().equals(binary))
                .forEach(this::deleteIfExists);
    }

    private Optional<Path> findXml() throws IOException {
        return Files
                .walk(new File(xmlPath).toPath())
                .filter(p -> !p.getFileName().toString().equals(xml))
                .findFirst();
    }

    private Optional<Path> findBinary() throws IOException {
        return Files
                .walk(new File(binaryPath).toPath())
                .filter(p -> !p.getFileName().toString().equals(binary))
                .findFirst();
    }

    private void deleteIfExists(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
