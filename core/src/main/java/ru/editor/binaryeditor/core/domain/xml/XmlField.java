package ru.editor.binaryeditor.core.domain.xml;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Getter
@Accessors(fluent = true)
@Root(name = "field")
public class XmlField {

    @Attribute(name = "name")
    private String name;

    @Attribute(name = "type")
    private String type;

    @Attribute(name = "length", required = false)
    private String length;
}
