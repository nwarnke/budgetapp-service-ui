package com.trn.config;


import com.trn.IUserDao;
import com.trn.UserDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.inject.Inject;

@Configuration
public class DaoConfig {

    @Inject
    @Qualifier("jdbcFactory")
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Bean
    IUserDao userDao(){
        return new UserDao(namedParameterJdbcTemplate);
    }


}
