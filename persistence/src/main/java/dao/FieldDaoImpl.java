package dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import ru.editor.binaryeditor.core.dao.FieldDao;
import ru.editor.binaryeditor.core.domain.Field;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class FieldDaoImpl implements FieldDao {

    private final JdbcOperations jdbcTemplate;

    @Override
    public void insert(Field field) {
        final String query = "INSERT INTO field (id, binary_file_id, field_description_id, value, length) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(query, field.id(), field.binaryFileId(), field.fieldDescriptionId(), field.value(), field.length());
    }

    @Override
    public Integer getCapacity(UUID binaryFileId) {
        final String query = "SELECT sum(length) AS capacity FROM field WHERE binary_file_id = ?";
        return jdbcTemplate.query(query, this::toCapacity, binaryFileId);
    }

    @Override
    public List<Field> getAll(UUID binaryFileId) {
        final String query = "SELECT * FROM field WHERE binary_file_id = ? ORDER BY serial_number";
        return jdbcTemplate.query(query, this::toField, binaryFileId);
    }

    @Override
    public void clean() {
        jdbcTemplate.execute("DELETE FROM field");
    }

    @Override
    public List<Field> pagination(UUID segmentId, Long limit, Long offset) {
        final String query = "SELECT f.id, f.length, f.binary_file_id, f.field_description_id, f.value FROM field f " +
                "JOIN field_description fd ON f.field_description_id = fd.id " +
                "WHERE fd.segment_id = ? " +
                "ORDER BY serial_number " +
                "LIMIT ? OFFSET ?";
        return jdbcTemplate.query(query, this::toField, segmentId, limit, offset);
    }

    @Override
    public Integer getIntValue(UUID segmentId, String fieldName, int iteration) {
        final String query = "SELECT f.value FROM field_description fd " +
                "JOIN field f ON fd.id = f.field_description_id " +
                "WHERE fd.segment_id = ? AND fd.name = ? " +
                "LIMIT 1 OFFSET ?";
        return jdbcTemplate.query(query, this::toIntValue, segmentId, fieldName, iteration);
    }

    @Override
    public Integer getIntValue(String segmentName, String fieldName) {
        final String query = "SELECT f.value FROM segment s " +
                "JOIN field_description fd ON fd.segment_id = s.id " +
                "JOIN field f ON fd.id = f.field_description_id " +
                "WHERE s.name = ? AND fd.name = ? " +
                "LIMIT 1 OFFSET 0";

        return jdbcTemplate.query(query, this::toIntValue, segmentName, fieldName);
    }

    @Override
    public Long count(UUID segmentId) {
        final String query = "SELECT count(*) AS count FROM field_description fd " +
                "JOIN field f ON fd.id = f.field_description_id " +
                "WHERE fd.segment_id = ?";
        return jdbcTemplate.query(query, this::toCount, segmentId);
    }

    @Override
    public void updateValue(UUID fieldId, String newValue) {
        final String query = "UPDATE field SET value = ? WHERE id = ?";
        int execCode = jdbcTemplate.update(query, newValue, fieldId);
        if (execCode == 0) {
            throw new RuntimeException("Failed to update the field: fieldId=" + fieldId);
        }
    }

    private Field toField(ResultSet resultSet, int i) throws SQLException {
        return Field.builder()
                .id((UUID) resultSet.getObject("id"))
                .length(resultSet.getInt("length"))
                .binaryFileId((UUID) resultSet.getObject("binary_file_id"))
                .fieldDescriptionId((UUID) resultSet.getObject("field_description_id"))
                .value(resultSet.getString("value"))
                .build();
    }

    private Integer toIntValue(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            throw new RuntimeException("Field value not found");
        }
        return resultSet.getInt("value");
    }

    private Long toCount(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            throw new RuntimeException("Fields count not found");
        }
        return resultSet.getLong("count");
    }

    private Integer toCapacity(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            throw new RuntimeException("Capacity count not found");
        }
        return resultSet.getInt("capacity");
    }

}
