package com.config;


import com.dao.IAccountDao;
import com.dao.IBudgetDao;
import com.dao.IUserDao;
import com.dao.impl.AccountDao;
import com.dao.impl.BudgetDao;
import com.dao.impl.UserDao;
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

    @Bean
    IBudgetDao budgetDao(){
        return new BudgetDao(namedParameterJdbcTemplate);
    }

    @Bean
    IAccountDao accountDao(){
        return new AccountDao(namedParameterJdbcTemplate);
    }

}
