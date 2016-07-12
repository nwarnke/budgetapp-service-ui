package com.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.SQLException;

@Configuration
public class JdbcConfig {

    @Bean
    public NamedParameterJdbcTemplate jdbcFactory(@Qualifier("dataSource") final DriverManagerDataSource dataSource) {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    @Bean
    DriverManagerDataSource dataSource() throws SQLException {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("org.postgresql.Driver");
        driverManagerDataSource.setUrl("jdbc:postgresql://ec2-184-73-202-229.compute-1.amazonaws.com:5432/d7ocia487h1sg5?sslmode=require");
        driverManagerDataSource.setUsername("jvoqrxltmntqtg");
        driverManagerDataSource.setPassword("fA4Moae5d8OXbgm2aYWevuj91z");
        return driverManagerDataSource;
    }

}
