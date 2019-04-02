package ru.editor.binaryeditor.server.controllers;

import lombok.Builder;
import lombok.Getter;
import ru.editor.binaryeditor.core.domain.BinaryFile;
import ru.editor.binaryeditor.core.domain.Paths;

@Getter
@Builder
public class SaveBinaryFile {

  private final Paths paths;

  private final BinaryFile binaryFile;
}
