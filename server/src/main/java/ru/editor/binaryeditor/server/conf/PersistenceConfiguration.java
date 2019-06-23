package ru.editor.binaryeditor.server.conf;

import dao.TestDaoImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class PersistenceConfiguration {

    @Bean(initMethod = "init")
    public TestDaoImpl testDao(JdbcTemplate jdbcTemplate) {
        return new TestDaoImpl(jdbcTemplate);
    }
}
