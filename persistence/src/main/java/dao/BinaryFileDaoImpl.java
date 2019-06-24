package dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import ru.editor.binaryeditor.core.dao.BinaryFileDao;
import ru.editor.binaryeditor.core.domain.BinaryFile;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class BinaryFileDaoImpl implements BinaryFileDao {

    private final JdbcOperations jdbcTemplate;

    @Override
    public byte[] getBody() {
        final String query = "SELECT body FROM binary_file LIMIT 1";
        return jdbcTemplate.query(query, this::toBinaryFileBody);
    }

    @Override
    public void updateBody(UUID binaryFileId, byte[] body) {
        final String query = "UPDATE binary_file SET body = ? WHERE id = ?";
        int execCode = jdbcTemplate.update(query, body, binaryFileId);
        if (execCode == 0) {
            throw new RuntimeException("Failed to update the binaryFile: binaryFileId=" + binaryFileId);
        }
    }

    @Override
    public void insert(BinaryFile binaryFile) {
        final String query = "INSERT INTO binary_file (id, name, body) VALUES (?, ?, ?)";
        jdbcTemplate.update(query, binaryFile.id(), binaryFile.name(), binaryFile.body());
    }

    @Override
    public void clean() {
        jdbcTemplate.execute("DELETE FROM binary_file");
    }

    @Override
    public Optional<BinaryFile> find() {
        final String query = "SELECT id, name FROM binary_file LIMIT 1";
        return jdbcTemplate.query(query, this::toBinaryFile);
    }

    @Override
    public BinaryFile getWithBody() {
        final String query = "SELECT id, name, body FROM binary_file LIMIT 1";
        return jdbcTemplate.query(query, this::toBinaryFileWithBody);
    }

    @Override
    public BinaryFile get() {
        return find().orElseThrow(() -> new RuntimeException("BinaryFile not found"));
    }

    private Optional<BinaryFile> toBinaryFile(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return Optional.empty();
        }
        return Optional.of(BinaryFile.builder()
                .id(UUID.fromString(resultSet.getString("id")))
                .name(resultSet.getString("name"))
                .build());
    }

    private BinaryFile toBinaryFileWithBody(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            throw new RuntimeException("BinaryFile not found");
        }
        return BinaryFile.builder()
                .id(UUID.fromString(resultSet.getString("id")))
                .name(resultSet.getString("name"))
                .body(resultSet.getBytes("body"))
                .build();
    }

    private byte[] toBinaryFileBody(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            throw new RuntimeException("BinaryFile not found");
        }
        return resultSet.getBytes("body");
    }
}
