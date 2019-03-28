package ru.editor.binaryeditor.server.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.editor.binaryeditor.core.domain.BinaryFile;
import ru.editor.binaryeditor.server.services.EditorOperations;

@RestController
@RequiredArgsConstructor
public class EditorController {

  private final EditorOperations editorOperations;

  @GetMapping("/binary-file")
  @ResponseStatus(HttpStatus.OK)
  public BinaryFile getBinaryFile() throws Exception {
    return editorOperations.getBinaryFile();
  }

  @PostMapping("/binary-file")
  @ResponseStatus(HttpStatus.OK)
  public BinaryFile createBinaryFile(@RequestBody BinaryFile binaryFile) throws Exception {
    return editorOperations.createBinaryFile(binaryFile);
  }

  @GetMapping("/paths")
  @ResponseStatus(HttpStatus.OK)
  public Paths getPaths() {
    return editorOperations.getPaths();
  }

  @PostMapping("/paths")
  @ResponseStatus(HttpStatus.OK)
  public Paths createPaths(@RequestBody Paths paths) {
    return editorOperations.createPaths(paths);
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
