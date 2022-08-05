package com.sk.op.application.config.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.cloud.config.server.environment.JdbcEnvironmentProperties;
import org.springframework.cloud.config.server.environment.JdbcEnvironmentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class BeanConfiguration {

    @Bean
    public EnvironmentRepository environmentRepository(JdbcTemplate jdbcTemplate, JdbcEnvironmentProperties jdbcEnvironmentProperties) {
        return new JdbcEnvironmentRepository(jdbcTemplate, jdbcEnvironmentProperties, new JdbcEnvironmentRepository.PropertiesResultSetExtractor());
    }
}
