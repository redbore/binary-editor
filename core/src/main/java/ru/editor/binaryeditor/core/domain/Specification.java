package ru.editor.binaryeditor.core.domain;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.UUID;

@Getter
@Builder
@ToString
@Accessors(fluent = true)
@EqualsAndHashCode
public class Specification {
    @Setter
    private UUID id;

    private String name;

    private byte[] body;
}
