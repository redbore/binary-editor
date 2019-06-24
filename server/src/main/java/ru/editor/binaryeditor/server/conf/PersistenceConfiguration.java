package ru.editor.binaryeditor.server.conf;

import dao.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import ru.editor.binaryeditor.core.dao.*;

@Configuration
public class PersistenceConfiguration {

    @Bean
    public SpecificationDao specificationDao(JdbcOperations jdbcTemplate) {
        return new SpecificationDaoImpl(jdbcTemplate);
    }

    @Bean
    public BinaryFileDao binaryFileDao(JdbcOperations jdbcTemplate) {
        return new BinaryFileDaoImpl(jdbcTemplate);
    }

    @Bean
    public SegmentDao segmentDao(JdbcOperations jdbcTemplate) {
        return new SegmentDaoImpl(jdbcTemplate);
    }

    @Bean
    public FieldDescriptionDao fieldDescriptionDao(JdbcOperations jdbcTemplate) {
        return new FieldDescriptionDaoImpl(jdbcTemplate);
    }

    @Bean
    public FieldDao fieldDao(JdbcOperations jdbcTemplate) {
        return new FieldDaoImpl(jdbcTemplate);
    }
}
