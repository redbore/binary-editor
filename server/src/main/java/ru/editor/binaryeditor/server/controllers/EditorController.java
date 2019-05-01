package ru.editor.binaryeditor.server.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.editor.binaryeditor.core.services.Editor;
import ru.editor.binaryeditor.server.controllers.dto.EditorDto;
import ru.editor.binaryeditor.server.controllers.dto.PathsDto;

import java.util.UUID;

import static ru.editor.binaryeditor.server.controllers.Mapper.*;

@Log4j2
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class EditorController {

  private final Editor editor;

  @PostMapping("/tables/{tableId}")
  @ResponseStatus(HttpStatus.OK)
  public void selectTable(@PathVariable UUID tableId) {
    editor.selectTable(tableId);
  }

  @GetMapping("/file")
  @ResponseStatus(HttpStatus.OK)
  public EditorDto getView() {
    return toEditorDto(editor.binaryFile(), editor.selectedTable());
  }

  @PostMapping("/file/open")
  @ResponseStatus(HttpStatus.OK)
  public void openBinaryFile(@RequestBody PathsDto paths) throws Exception {
    editor.openFile(toPaths(paths));
  }

  @PostMapping("/file/save")
  @ResponseStatus(HttpStatus.OK)
  public void saveBinaryFile(@RequestBody PathsDto paths) throws Exception {
    editor.saveFile(toPaths(paths));
  }

  @GetMapping("/paths")
  @ResponseStatus(HttpStatus.OK)
  public PathsDto getPaths() {
    return toPathsDto(editor.paths());
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
  public String exception(Exception e) {
    log.info(e.getMessage());
    return e.getMessage();
  }

}
