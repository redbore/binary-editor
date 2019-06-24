package ru.editor.binaryeditor.core.domain.xml;


import lombok.Getter;
import lombok.experimental.Accessors;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Getter
@Accessors(fluent = true)
@Root(name = "segment")
public class XmlSegment {

    @Attribute(name = "name")
    private String name;

    @Attribute(name = "count", required = false)
    private String count;

    @ElementList(inline = true, name = "field")
    private List<XmlField> xmlFields;
}
