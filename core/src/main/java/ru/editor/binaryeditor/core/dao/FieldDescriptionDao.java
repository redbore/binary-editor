package ru.editor.binaryeditor.core.dao;

import ru.editor.binaryeditor.core.domain.FieldDescription;

import java.util.List;
import java.util.UUID;

public interface FieldDescriptionDao {

    void insert(List<FieldDescription> fieldDescriptions);

    void clean();

    List<FieldDescription> getAllBySegmentId(UUID segmentId);

    List<FieldDescription> getAllBySpecificationId(UUID binaryFileId);

    Long getCount(UUID segmentId);
}
