package com.trn.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.ConnectionProperties;

import java.sql.SQLException;
import java.util.Properties;

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
        
        return driverManagerDataSource;
    }

}
