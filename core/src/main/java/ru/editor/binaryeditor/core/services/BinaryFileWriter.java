package ru.editor.binaryeditor.core.services;

import lombok.RequiredArgsConstructor;
import ru.editor.binaryeditor.core.dao.BinaryFileDao;
import ru.editor.binaryeditor.core.dao.FieldDao;
import ru.editor.binaryeditor.core.dao.FieldDescriptionDao;
import ru.editor.binaryeditor.core.domain.Field;
import ru.editor.binaryeditor.core.domain.FieldDescription;
import ru.editor.binaryeditor.core.services.interfaces.FileWriter;
import ru.editor.binaryeditor.core.services.interfaces.TypeWriter;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static ru.editor.binaryeditor.core.services.Type.type;
import static ru.editor.binaryeditor.core.services.TypeHandler.writer;

@RequiredArgsConstructor
public class BinaryFileWriter implements FileWriter {

    private final FieldDao fieldDao;
    private final BinaryFileDao binaryFileDao;
    private final FieldDescriptionDao fieldDescriptionDao;

    public void write(UUID binaryFileId, UUID specificationId) {

        List<Field> fields = fieldDao.getAll(binaryFileId);
        Map<UUID, FieldDescription> fieldDescriptions = fieldDescriptionDao.getAllBySpecificationId(specificationId).stream()
                .collect(Collectors.toMap(FieldDescription::id, fd -> fd));

        int size = fieldDao.getCapacity(binaryFileId);
        ByteBuffer buffer = ByteBuffer.allocate(size);

        fields.forEach(field -> {
            FieldDescription fieldDescription = Optional.ofNullable(fieldDescriptions.get(field.fieldDescriptionId()))
                    .orElseThrow(() -> new RuntimeException("FieldDescription not found: fieldId=" + field.id()));

            Type type = type(fieldDescription.type());
            TypeWriter typeWriter = writer(type);
            typeWriter.write(buffer, field.value(), field.length());
        });

        binaryFileDao.updateBody(binaryFileId, buffer.array());
    }
}
