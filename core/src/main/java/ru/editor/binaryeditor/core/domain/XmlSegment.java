package ru.editor.binaryeditor.core.domain;

import java.util.List;
import lombok.Getter;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Getter
@Root(name = "segment")
public class XmlSegment {

  @Attribute(name = "name")
  private String name;

  @Attribute(name = "count", required = false)
  private String count;

  @ElementList(inline = true, name = "field")
  private List<XmlField> xmlFields;

  public XmlField findXmlField(String xmlFieldName) {
    return xmlFields.stream()
        .filter(type -> type.getName().equals(xmlFieldName))
        .findFirst()
        .orElseThrow(RuntimeException::new);
  }


}
