package dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcOperations;
import ru.editor.binaryeditor.core.dao.FieldDescriptionDao;
import ru.editor.binaryeditor.core.domain.FieldDescription;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class FieldDescriptionDaoImpl implements FieldDescriptionDao {

    private final JdbcOperations jdbcTemplate;

    @Override
    public void insert(List<FieldDescription> fieldDescriptions) {
        final String query = "INSERT INTO field_description " +
                "(id, segment_id, name, type, length_link) " +
                "VALUES(?, ?, ?, ?, ?)";
        jdbcTemplate.batchUpdate(query, toBatchFieldDescription(fieldDescriptions));
    }

    @Override
    public void clean() {
        jdbcTemplate.execute("DELETE FROM field_description");
    }

    @Override
    public List<FieldDescription> getAllBySegmentId(UUID segmentId) {
        final String query = "SELECT * FROM field_description fd WHERE fd.segment_id = ?";
        List<FieldDescription> fieldDescriptions = jdbcTemplate.query(query, this::toFieldDescription, segmentId);

        if (fieldDescriptions.isEmpty()) {
            throw new RuntimeException("FieldDescription not found: segmentId=" + segmentId);
        }
        return fieldDescriptions;
    }

    @Override
    public List<FieldDescription> getAllBySpecificationId(UUID specificationId) {
        final String query = "SELECT fd.id, fd.segment_id, fd.name, fd.type, fd.length_link FROM segment s " +
                "JOIN field_description fd ON s.id = fd.segment_id " +
                "WHERE s.specification_id = ?";
        List<FieldDescription> fieldDescriptions = jdbcTemplate.query(query, this::toFieldDescription, specificationId);

        if (fieldDescriptions.isEmpty()) {
            throw new RuntimeException("FieldDescription not found: specificationId=" + specificationId);
        }
        return fieldDescriptions;
    }

    @Override
    public Long getCount(UUID segmentId) {
        final String query = "SELECT count(*) AS count FROM field_description WHERE segment_id = ?";
        return jdbcTemplate.query(query, this::toCount, segmentId);
    }

    private FieldDescription toFieldDescription(ResultSet resultSet, int i) throws SQLException {
        return FieldDescription.builder()
                .id((UUID) resultSet.getObject("id"))
                .segmentId((UUID) resultSet.getObject("segment_id"))
                .name(resultSet.getString("name"))
                .type(resultSet.getString("type"))
                .lengthLink(resultSet.getString("length_link"))
                .build();
    }

    private BatchPreparedStatementSetter toBatchFieldDescription(List<FieldDescription> fieldDescriptions) {
        return new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                FieldDescription fieldDescription = fieldDescriptions.get(i);
                preparedStatement.setObject(1, fieldDescription.id());
                preparedStatement.setObject(2, fieldDescription.segmentId());
                preparedStatement.setString(3, fieldDescription.name());
                preparedStatement.setString(4, fieldDescription.type());
                preparedStatement.setString(5, fieldDescription.lengthLink());
            }

            public int getBatchSize() {
                return fieldDescriptions.size();
            }
        };
    }

    private Long toCount(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            throw new RuntimeException("FieldDescriptions count not found");
        }
        return resultSet.getLong("count");
    }
}
