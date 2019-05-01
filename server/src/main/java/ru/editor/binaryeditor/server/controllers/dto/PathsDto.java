package ru.editor.binaryeditor.server.controllers.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PathsDto {

    private final String binary;

    private final String xml;
}
