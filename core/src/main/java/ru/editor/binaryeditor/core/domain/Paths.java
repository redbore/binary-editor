package ru.editor.binaryeditor.core.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Paths {

  private String binary;

  private String xml;
}
