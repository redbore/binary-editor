package ru.editor.binaryeditor.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import ru.editor.binaryeditor.core.dao.*;
import ru.editor.binaryeditor.core.domain.*;
import ru.editor.binaryeditor.core.services.interfaces.FileReader;
import ru.editor.binaryeditor.core.services.interfaces.FileWriter;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.UUID.randomUUID;

@RequiredArgsConstructor
public class Editor {

    private final SpecificationDao specificationDao;
    private final BinaryFileDao binaryFileDao;
    private final SegmentDao segmentDao;
    private final FileReader binaryFileReader;
    private final FileReader specificationReader;
    private final FieldDescriptionDao fieldDescriptionDao;
    private final FieldDao fieldDao;
    private final FileWriter binaryFileWriter;

    @Transactional
    public View view() {
        Optional<Specification> specificationOptional = specificationDao.find();
        Optional<BinaryFile> binaryFileOptional = binaryFileDao.find();

        if (!specificationOptional.isPresent() || !binaryFileOptional.isPresent()) {
            return View.empty();
        }
        Specification specification = specificationOptional.get();
        BinaryFile binaryFile = binaryFileOptional.get();

        List<Segment> segments = segmentDao.getAll(specification.id());
        List<FieldDescription> fieldDescriptions = fieldDescriptionDao
                .getAllBySpecificationId(specification.id());

        Map<UUID, List<FieldDescription>> fieldDescriptionsMap = fieldDescriptions.stream()
                .collect(Collectors.groupingBy(FieldDescription::segmentId));

        Map<Segment, SegmentInfo> segmentInfoMap = segments.stream()
                .collect(Collectors.toMap(
                        segment -> segment,
                        segment -> SegmentInfo.builder()
                                .fieldCount(fieldDao.count(segment.id()))
                                .fieldDescriptions(fieldDescriptionsMap.get(segment.id()))
                                .build(),
                        (segment, segment1) -> segment,
                        LinkedHashMap::new
                ));

        return View.builder()
                .binaryFile(binaryFile)
                .specification(specification)
                .segments(segmentInfoMap)
                .build();
    }

    @Transactional
    public List<Field> pagination(UUID segmentId, Long limit, Long offset) {
        return fieldDao.pagination(segmentId, limit, offset);
    }

    @Transactional
    public void fieldEdit(UUID fieldId, String newValue) {
        fieldDao.updateValue(fieldId, newValue);
    }

    @Transactional
    public void open(BinaryFile binaryFile, Specification specification) throws Exception {
        binaryFile = binaryFile.id() != null ? binaryFileDao.getWithBody() : binaryFile.id(randomUUID());
        specification = specification.id() != null ? specificationDao.getWithBody() : specification.id(randomUUID());
        clean();
        specificationDao.insert(specification);
        binaryFileDao.insert(binaryFile);

        specificationReader.read(binaryFile.id(), specification.id());
        binaryFileReader.read(binaryFile.id(), specification.id());
    }

    @Transactional
    public BinaryFile save() { // send Ids
        BinaryFile binaryFile = binaryFileDao.get();
        Specification specification = specificationDao.get();

        binaryFileWriter.write(binaryFile.id(), specification.id());
        return binaryFileDao.getWithBody();
    }

    private void clean() {
        fieldDao.clean();
        fieldDescriptionDao.clean();
        segmentDao.clean();
        specificationDao.clean();
        binaryFileDao.clean();
    }

    public Long getFieldCount(UUID segmentId) {
        return fieldDescriptionDao.getCount(segmentId);
    }

    public List<FieldDescription> getFieldDescriptions(UUID segmentId) {
        return fieldDescriptionDao.getAllBySegmentId(segmentId);
    }
}
