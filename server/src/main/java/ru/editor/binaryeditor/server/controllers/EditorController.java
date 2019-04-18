package ru.editor.binaryeditor.server.controllers;

import static ru.editor.binaryeditor.server.controllers.Mapper.toEditorDto;
import static ru.editor.binaryeditor.server.controllers.Mapper.toPaths;
import static ru.editor.binaryeditor.server.controllers.Mapper.toPathsDto;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.editor.binaryeditor.core.services.Editor;
import ru.editor.binaryeditor.server.controllers.dto.EditorDto;
import ru.editor.binaryeditor.server.controllers.dto.PathsDto;

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
    return toEditorDto(editor.getBinaryFile(), editor.getSelectedTable());
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
    return toPathsDto(editor.getPaths());
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

}
