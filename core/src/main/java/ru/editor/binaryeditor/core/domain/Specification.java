package ru.editor.binaryeditor.core.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.UUID;

@Getter
@Builder
@ToString
@Accessors(fluent = true)
@EqualsAndHashCode
public class Specification {
    private UUID id;

    private String name;

    private byte[] body;

    public Specification id(UUID id) {
        this.id = id;
        return this;
    }
}
