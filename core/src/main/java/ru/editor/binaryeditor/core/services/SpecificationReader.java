package ru.editor.binaryeditor.core.services;

import lombok.RequiredArgsConstructor;
import org.simpleframework.xml.core.Persister;
import ru.editor.binaryeditor.core.dao.FieldDescriptionDao;
import ru.editor.binaryeditor.core.dao.SegmentDao;
import ru.editor.binaryeditor.core.dao.SpecificationDao;
import ru.editor.binaryeditor.core.domain.FieldDescription;
import ru.editor.binaryeditor.core.domain.Segment;
import ru.editor.binaryeditor.core.domain.xml.XmlField;
import ru.editor.binaryeditor.core.domain.xml.XmlFile;
import ru.editor.binaryeditor.core.domain.xml.XmlSegment;
import ru.editor.binaryeditor.core.services.interfaces.FileReader;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class SpecificationReader implements FileReader {

    private final SpecificationDao specificationDao;
    private final SegmentDao segmentDao;
    private final FieldDescriptionDao fieldDescriptionDao;

    public void read(UUID binaryFileId, UUID specificationId) throws Exception {
        byte[] specificationBody = specificationDao.getBody();
        XmlFile xmlFile = new Persister().read(XmlFile.class, new ByteArrayInputStream(specificationBody));

        List<Segment> segments = new ArrayList<>();
        List<FieldDescription> fieldDescriptions = new ArrayList<>();

        fillSegments(segments, specificationId, xmlFile, fieldDescriptions);

        segmentDao.insert(segments);
        fieldDescriptionDao.insert(fieldDescriptions);
    }

    private void fillSegments(
            List<Segment> segments, UUID specificationId, XmlFile xmlFile, List<FieldDescription> fieldDescriptions
    ) {
        xmlFile.xmlSegments().forEach(xmlSegment -> {
                    Segment segment = toSegment(specificationId, xmlSegment);
                    segments.add(segment);

                    xmlSegment.xmlFields().forEach(xmlField ->
                            fieldDescriptions.add(toFieldDescription(segment.id(), xmlField)));
                }
        );
    }

    private Segment toSegment(UUID specificationId, XmlSegment xmlSegment) {
        return Segment.builder()
                .id(UUID.randomUUID())
                .specificationId(specificationId)
                .name(xmlSegment.name())
                .countLink(xmlSegment.count())
                .build();
    }

    private FieldDescription toFieldDescription(UUID segmentId, XmlField xmlField) {
        return FieldDescription.builder()
                .id(UUID.randomUUID())
                .segmentId(segmentId)
                .name(xmlField.name())
                .type(xmlField.type())
                .lengthLink(xmlField.length())
                .build();
    }
}
