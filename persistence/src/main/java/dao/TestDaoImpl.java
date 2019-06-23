package dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;

@RequiredArgsConstructor
public class TestDaoImpl {

    private final JdbcTemplate jdbcTemplate;

    public void init() {
        jdbcTemplate.query("SELECT * FROM TEST", (f) -> {
            System.out.println(f.getLong("id"));
        });
    }
}
