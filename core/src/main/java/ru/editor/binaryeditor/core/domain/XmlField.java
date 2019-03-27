package ru.editor.binaryeditor.core.domain;

import lombok.Getter;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Getter
@Root(name = "field")
public class XmlField {

  @Attribute(name = "name")
  private String name;

  @Attribute(name = "type")
  private String type;
  /**
   * int, float default 4, for string new value
   */
  @Attribute(name = "length", required = false)
  private Integer length = 4;
}
