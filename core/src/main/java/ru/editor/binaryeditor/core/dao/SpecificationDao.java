package ru.editor.binaryeditor.core.dao;

import ru.editor.binaryeditor.core.domain.Specification;

import java.util.Optional;

public interface SpecificationDao {

    byte[] getBody();

    void insert(Specification specification);

    void clean();

    Optional<Specification> find();

    Specification get();
}
