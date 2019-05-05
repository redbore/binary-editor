package ru.editor.binaryeditor.server.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.editor.binaryeditor.core.domain.EditorFile;
import ru.editor.binaryeditor.core.services.CachedFileService;
import ru.editor.binaryeditor.core.services.Editor;
import ru.editor.binaryeditor.core.services.type.TypeConverter;
import ru.editor.binaryeditor.server.controllers.dto.AvailableFiles;
import ru.editor.binaryeditor.server.controllers.dto.EditorDto;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

import static ru.editor.binaryeditor.server.controllers.Mapper.toEditorDto;

@Log4j2
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class EditorController {

    private final Editor editor;
    private final CachedFileService cachedFileService;

    @PostMapping("/tables/{tableId}")
    @ResponseStatus(HttpStatus.OK)
    public void selectTable(@PathVariable UUID tableId) {
        editor.selectTable(tableId);
    }

    @GetMapping("/files")
    @ResponseStatus(HttpStatus.OK)
    public EditorDto getView() {
        return toEditorDto(editor.view(), editor.selectedTable());
    }

    @PostMapping("/files/open")
    @ResponseStatus(HttpStatus.OK)
    public void openBinaryFile() throws Exception {
        editor.openBinaryFile();
    }

    @GetMapping("/files/save")
    @ResponseStatus(HttpStatus.OK)
    public EditorFile saveBinaryFile() throws Exception {
        return editor.saveBinaryFile();
    }

    @GetMapping("/files/available")
    @ResponseStatus(HttpStatus.OK)
    public AvailableFiles getActualFiles() throws IOException {
        Path binaryPath = cachedFileService.binaryPath();
        Path xmlPath = cachedFileService.xmlPath();
        return AvailableFiles.builder()
                .binaryName(binaryPath == null ? null : binaryPath.getFileName().toString())
                .xmlName(xmlPath == null ? null : xmlPath.getFileName().toString())
                .build();
    }

    @PostMapping("/files/binary/update")
    @ResponseStatus(HttpStatus.OK)
    public void updateBinaryFile(@RequestBody EditorFile editorFile) throws Exception {
        cachedFileService.updateBinaryFile(editorFile.getName(), TypeConverter.toByteArray(editorFile.getBody()));
    }

    @PostMapping("/files/xml/update")
    @ResponseStatus(HttpStatus.OK)
    public void updateXmlFile(@RequestBody EditorFile editorFile) throws Exception {
        cachedFileService.updateXmlFile(editorFile.getName(), TypeConverter.toByteArray(editorFile.getBody()));
    }

    @PostMapping("/tables/{tableId}/rows/{rowId}/fields/{fieldId}")
    @ResponseStatus(HttpStatus.OK)
    public void editField(
            @PathVariable UUID tableId,
            @PathVariable UUID rowId,
            @PathVariable UUID fieldId,
            @RequestBody String value
    ) {
        editor.editField(tableId, rowId, fieldId, value);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void exception(Exception e) {
        log.error(e);
    }
}
