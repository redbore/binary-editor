package ru.editor.binaryeditor.server.controllers.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class FieldDto {

    private final UUID uuid;

    private final String name;

    private final Object value;

}
