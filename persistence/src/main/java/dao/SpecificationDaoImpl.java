package dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import ru.editor.binaryeditor.core.dao.SpecificationDao;
import ru.editor.binaryeditor.core.domain.Specification;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class SpecificationDaoImpl implements SpecificationDao {

    private final JdbcOperations jdbcTemplate;

    @Override
    public byte[] getBody() {
        final String query = "SELECT body FROM specification LIMIT 1";
        return jdbcTemplate.query(query, this::toSpecificationBody);
    }

    @Override
    public void insert(Specification specification) {
        final String query = "INSERT INTO specification (id, name, body) VALUES (?, ?, ?)";
        jdbcTemplate.update(query, specification.id(), specification.name(), specification.body());
    }

    @Override
    public void clean() {
        jdbcTemplate.execute("DELETE FROM specification");
    }

    @Override
    public Optional<Specification> find() {
        final String query = "SELECT id, name FROM specification LIMIT 1";
        return jdbcTemplate.query(query, this::toSpecification);
    }

    @Override
    public Specification get() {
        return find().orElseThrow(() -> new RuntimeException("Specification not found"));
    }

    private Optional<Specification> toSpecification(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return Optional.empty();
        }
        return Optional.of(Specification.builder()
                .id(UUID.fromString(resultSet.getString("id")))
                .name(resultSet.getString("name"))
                .build());
    }

    private byte[] toSpecificationBody(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            throw new RuntimeException("BinaryFile not found");
        }
        return resultSet.getBytes("body");
    }

}
