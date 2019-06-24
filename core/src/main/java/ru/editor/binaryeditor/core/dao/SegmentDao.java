package ru.editor.binaryeditor.core.dao;

import ru.editor.binaryeditor.core.domain.Segment;

import java.util.List;
import java.util.UUID;

public interface SegmentDao {

    void insert(List<Segment> segments);

    void clean();

    List<Segment> getAll(UUID specificationId);
}
