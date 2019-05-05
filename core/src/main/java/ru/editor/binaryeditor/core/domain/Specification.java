package ru.editor.binaryeditor.core.domain;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Getter
@Accessors(fluent = true)
@Root(name = "segments")
public class Specification {

    @ElementList(inline = true, name = "segment")
    private List<XmlSegment> xmlSegments;

    public XmlSegment getXmlSegment(String xmlSegmentName) {
        return xmlSegments.stream()
                .filter(xmlSegments -> xmlSegments.name().equals(xmlSegmentName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Xml segment not found: name=" + xmlSegmentName));
    }
}
