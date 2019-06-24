package ru.editor.binaryeditor.core.domain.xml;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Getter
@Accessors(fluent = true)
@Root(name = "segments")
public class XmlFile {

    @ElementList(inline = true, name = "segment")
    private List<XmlSegment> xmlSegments;
}

