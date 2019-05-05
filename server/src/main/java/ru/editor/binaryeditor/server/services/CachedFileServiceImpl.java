package ru.editor.binaryeditor.server.services;

import ru.editor.binaryeditor.core.services.CachedFileService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class CachedFileServiceImpl implements CachedFileService {

    private static final String specification = "specification";
    private static final String binary = "binary";
    private static final String specificationPath = "cache\\" + specification;
    private static final String binaryPath = "cache\\" + binary;

    @Override
    public Path updateBinary(String name, byte[] body) throws IOException {
        deleteBinaryFiles();
        Path filePath = new File(binaryPath + "/" + name).toPath();
        Files.createFile(filePath);
        Files.write(filePath, body);
        return filePath;
    }

    @Override
    public Path updateBinary(byte[] body) throws IOException {
        Path filePath = findBinaryPath().orElseThrow(() -> new RuntimeException("Binary file not found in cache"));
        deleteBinaryFiles();
        Files.createFile(filePath);
        Files.write(filePath, body);
        return filePath;
    }

    @Override
    public Path updateSpecification(String name, byte[] body) throws IOException {
        deleteSpecificationFiles();
        Path filePath = new File(specificationPath + "/" + name).toPath();
        Files.createFile(filePath);
        Files.write(filePath, body);
        return filePath;
    }

    @Override
    public byte[] getBinaryFile() throws IOException {
        Path filePath = findBinaryPath().orElseThrow(() -> new RuntimeException("Binary file not found in cache"));
        return Files.readAllBytes(filePath);
    }

    @Override
    public Path binaryPath() throws IOException {
        return findBinaryPath().orElse(null);
    }

    @Override
    public Path specificationPath() throws IOException {
        return findSpecificationPath().orElse(null);
    }

    private void deleteSpecificationFiles() throws IOException {
        Files
                .walk(new File(specificationPath).toPath())
                .filter(p -> !p.getFileName().toString().equals(specification))
                .forEach(this::deleteIfExists);
    }

    private void deleteBinaryFiles() throws IOException {
        Files
                .walk(new File(binaryPath).toPath())
                .filter(p -> !p.getFileName().toString().equals(binary))
                .forEach(this::deleteIfExists);
    }

    private Optional<Path> findSpecificationPath() throws IOException {
        return Files
                .walk(new File(specificationPath).toPath())
                .filter(p -> !p.getFileName().toString().equals(specification))
                .findFirst();
    }

    private Optional<Path> findBinaryPath() throws IOException {
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
