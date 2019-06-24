package ru.editor.binaryeditor.core.services;

import lombok.RequiredArgsConstructor;
import ru.editor.binaryeditor.core.dao.BinaryFileDao;
import ru.editor.binaryeditor.core.dao.FieldDao;
import ru.editor.binaryeditor.core.dao.FieldDescriptionDao;
import ru.editor.binaryeditor.core.dao.SegmentDao;
import ru.editor.binaryeditor.core.domain.Field;
import ru.editor.binaryeditor.core.domain.FieldDescription;
import ru.editor.binaryeditor.core.domain.FieldLink;
import ru.editor.binaryeditor.core.domain.Segment;
import ru.editor.binaryeditor.core.services.interfaces.FileReader;
import ru.editor.binaryeditor.core.services.interfaces.TypeReader;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Integer.parseInt;
import static ru.editor.binaryeditor.core.services.Type.type;
import static ru.editor.binaryeditor.core.services.TypeHandler.reader;

@RequiredArgsConstructor
public class BinaryFileReader implements FileReader {

    private final FieldDao fieldDao;
    private final SegmentDao segmentDao;
    private final BinaryFileDao binaryFileDao;
    private final FieldDescriptionDao fieldDescriptionDao;

    public void read(UUID binaryFileId, UUID specificationId) {
        byte[] body = binaryFileDao.getBody();
        AtomicInteger offset = new AtomicInteger();

        List<Segment> segments = segmentDao.getAll(specificationId);

        readSegments(segments, binaryFileId, body, offset);
    }

    private void readSegments(List<Segment> segments, UUID binaryFileId, byte[] body, AtomicInteger offset) {
        segments.forEach(segment -> {

            int count = countByLink(segment); // repeat count

            List<FieldDescription> fieldDescriptions = fieldDescriptionDao.getAllBySegmentId(segment.id());

            for (int i = 0; i < count; i++) {
                readFields(fieldDescriptions, binaryFileId, body, offset, i);
            }
        });
    }

    private void readFields(
            List<FieldDescription> fieldDescriptions, UUID binaryFileId, byte[] body, AtomicInteger offset, int iteration
    ) {
        fieldDescriptions.forEach(fieldDescription -> {

            int length = lengthByLink(fieldDescription, iteration); //type length

            Type type = type(fieldDescription.type());
            TypeReader reader = reader(type);

            Object value = reader.read(body, offset, length);
            Field field = Field.builder()
                    .id(UUID.randomUUID())
                    .fieldDescriptionId(fieldDescription.id())
                    .binaryFileId(binaryFileId)
                    .length(length)
                    .value(value.toString())
                    .build();

            fieldDao.insert(field);
        });
    }

    private int countByLink(Segment segment) {
        String countLink = segment.countLink();

        if (countLink == null) { // if count not specified, then default
            return 1;
        }
        if (countLink.contains(".")) { // if link segmentName.fieldName
            FieldLink fieldLink = fieldLink(countLink);
            return fieldDao.getIntValue(fieldLink.segmentName(), fieldLink.fieldName());
        }
        return parseInt(countLink); // if count specified
    }

    private int lengthByLink(FieldDescription fieldDescription, int iteration) {
        String lengthLink = fieldDescription.lengthLink();

        if (lengthLink == null) { // if numeric type
            return type(fieldDescription.type()).length();
        }
        if (lengthLink.contains(".")) { // if string type with link segmentName.fieldName
            FieldLink fieldLink = fieldLink(lengthLink);

            return fieldLink.segmentName().equals("this") //if string type with link this.fieldName
                    ? fieldDao.getIntValue(fieldDescription.segmentId(), fieldLink.fieldName(), iteration)
                    : fieldDao.getIntValue(fieldLink.segmentName(), fieldLink.fieldName());
        }
        return parseInt(lengthLink);// if string type with specified length
    }

    private FieldLink fieldLink(String link) {
        // make validation
        String[] names = link.split("[.]");
        return FieldLink.builder()
                .segmentName(names[0])
                .fieldName(names[1])
                .build();
    }

}
