package ru.editor.binaryeditor.core.dao;

import ru.editor.binaryeditor.core.domain.Field;

import java.util.List;
import java.util.UUID;

public interface FieldDao {

    void insert(Field field);

    Integer getCapacity(UUID binaryFileId);

    List<Field> getAll(UUID binaryFileId);

    void clean();

    List<Field> pagination(UUID segmentId, Long limit, Long offset);

    /**
     * Find field value by segmentId, fieldName and segment iteration (this)
     *
     * @return length value
     */
    Integer getIntValue(UUID segmentId, String fieldName, int iteration);

    /**
     * Find first field value by segmentName and fieldName (link)
     *
     * @return length value
     */
    Integer getIntValue(String segmentName, String fieldName);

    Long count(UUID segmentId);

    void updateValue(UUID fieldId, String newValue);
}
