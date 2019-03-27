package ru.editor.binaryeditor.core.domain;

import java.util.List;
import lombok.Getter;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Getter
@Root(name = "segments")
public class XmlFile {

  @ElementList(inline = true, name = "segment")
  private List<XmlSegment> xmlSegments;

  public XmlSegment findXmlSegment(String xmlSegmentName) {
    return xmlSegments.stream()
        .filter(xmlSegments -> xmlSegments.getName().equals(xmlSegmentName))
        .findFirst()
        .orElseThrow(RuntimeException::new);
  }
}
