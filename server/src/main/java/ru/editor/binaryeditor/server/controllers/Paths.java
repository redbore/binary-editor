package ru.editor.binaryeditor.server.controllers;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Valid
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Paths {

  @NotNull
  private String binary;

  @NotNull
  private String xml;
}
