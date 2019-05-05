package ru.editor.binaryeditor.server.controllers.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AvailableFiles {

    private final String xmlName;

    private final String binaryName;

}
