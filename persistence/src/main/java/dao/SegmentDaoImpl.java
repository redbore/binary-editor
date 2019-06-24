package dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcOperations;
import ru.editor.binaryeditor.core.dao.SegmentDao;
import ru.editor.binaryeditor.core.domain.Segment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class SegmentDaoImpl implements SegmentDao {

    private final JdbcOperations jdbcTemplate;

    @Override
    public void insert(List<Segment> segments) {
        final String query = "INSERT INTO segment " +
                "(id, specification_id, count_link, name) " +
                "VALUES (?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(query, toBatchSegment(segments));
    }

    @Override
    public void clean() {
        jdbcTemplate.execute("DELETE FROM segment");
    }

    @Override
    public List<Segment> getAll(UUID specificationId) {
        final String query = "SELECT * FROM segment WHERE specification_id = ?";
        List<Segment> segments = jdbcTemplate.query(query, this::toSegment, specificationId);
        if (segments.isEmpty()) {
            throw new RuntimeException("Segments not found: specificationId=" + specificationId);
        }
        return segments;
    }

    private Segment toSegment(ResultSet resultSet, int i) throws SQLException {
        return Segment.builder()
                .id((UUID) resultSet.getObject("id"))
                .specificationId((UUID) resultSet.getObject("specification_id"))
                .countLink(resultSet.getString("count_link"))
                .name(resultSet.getString("name"))
                .build();
    }

    private BatchPreparedStatementSetter toBatchSegment(List<Segment> segments) {
        return new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                Segment segment = segments.get(i);
                preparedStatement.setObject(1, segment.id());
                preparedStatement.setObject(2, segment.specificationId());
                preparedStatement.setString(3, segment.countLink());
                preparedStatement.setString(4, segment.name());
            }

            public int getBatchSize() {
                return segments.size();
            }
        };
    }
}
