package ru.editor.binaryeditor.server.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.editor.binaryeditor.core.domain.BinaryFile;
import ru.editor.binaryeditor.core.domain.Paths;
import ru.editor.binaryeditor.server.services.EditorOperations;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class EditorController {

  private final EditorOperations editorOperations;

  @PostMapping("/binary-file")
  @ResponseStatus(HttpStatus.OK)
  public BinaryFile openBinaryFile(@RequestBody Paths paths) throws Exception {
    return editorOperations.openBinaryFile(paths);
  }

  @PutMapping("/binary-file")
  @ResponseStatus(HttpStatus.OK)
  public BinaryFile saveBinaryFile(@RequestBody SaveBinaryFile saveBinaryFile) throws Exception {
    return editorOperations.saveBinaryFile(saveBinaryFile);
  }

  @GetMapping("/paths")
  @ResponseStatus(HttpStatus.OK)
  public Paths getPaths() {
    return editorOperations.getPaths();
  }

  @PostMapping("/suicide")
  @ResponseStatus(HttpStatus.OK)
  public void suicide() {
    editorOperations.suicide();
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public String exceptionHandler(Exception e) {
    return e.getMessage();
  }
}
