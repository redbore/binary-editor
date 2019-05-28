package ru.editor.binaryeditor.core.domain;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;
import ru.editor.binaryeditor.core.services.type.FieldHandlerFactory;

@Getter
@Accessors(fluent = true)
@Root(name = "field")
public class XmlField {

    @Attribute(name = "name")
    private String name;

    @Attribute(name = "type")
    private String type;

    @Attribute(name = "length", required = false)
    private String lengthLink;

    private Integer length;

    public Integer length() {
        return length;
    }

    public Integer length(OpenedBinary openedBinary, FieldHandlerFactory fieldHandlerFactory) {

        if (lengthLink != null && lengthLink.contains(".")) {
            length = openedBinary.findByLink(lengthLink);
        } else if (type.equals("string")) {
            length = Integer.parseInt(lengthLink);
        } else {
            length = fieldHandlerFactory.length(type);
        }
        return length;
    }
}
