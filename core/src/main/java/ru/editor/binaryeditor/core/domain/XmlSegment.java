package ru.editor.binaryeditor.core.domain;

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
    private String countLink;

    private Integer count;

    @ElementList(inline = true, name = "field")
    private List<XmlField> xmlFields;

    public XmlField xmlField(String xmlFieldName) {
        return xmlFields.stream()
                .filter(type -> type.name().equals(xmlFieldName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Xml field not found: name=" + xmlFieldName));
    }

    public Integer count(OpenedBinary openedBinary) {
        if (countLink == null) {
            count = 1;
        } else if (countLink.contains(".")) {
            count = openedBinary.findByLink(countLink);
        } else {
            count = Integer.parseInt(countLink);
        }
        return count;
    }
}
