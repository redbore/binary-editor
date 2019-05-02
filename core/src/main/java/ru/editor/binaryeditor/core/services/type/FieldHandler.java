package ru.editor.binaryeditor.core.services.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
public class FieldHandler {

    private final FieldReader reader;

    private final FieldWriter writer;

    private final Integer length;
}
