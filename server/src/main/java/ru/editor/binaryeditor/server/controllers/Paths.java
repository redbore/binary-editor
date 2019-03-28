package ru.editor.binaryeditor.server.controllers;

import java.nio.file.Path;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Valid
@Getter
@Builder
public class Paths {
  @NonNull
  @NotNull
  private final Path binary;
  @NonNull
  @NotNull
  private final Path xml;
}
