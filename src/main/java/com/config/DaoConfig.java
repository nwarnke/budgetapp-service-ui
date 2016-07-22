package com.config;


import com.dao.IBudgetDao;
import com.dao.ICategoryDao;
import com.dao.ISubcategoryDao;
import com.dao.IUserDao;
import com.dao.impl.BudgetDao;
import com.dao.impl.CategoryDao;
import com.dao.impl.SubcategoryDao;
import com.dao.impl.UserDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.inject.Inject;

@Configuration
public class DaoConfig {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Inject
    public DaoConfig(@Qualifier("jdbcFactory") NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Bean
    IUserDao userDao(){
        return new UserDao(namedParameterJdbcTemplate);
    }

    @Bean
    IBudgetDao budgetDao(){
        return new BudgetDao(namedParameterJdbcTemplate);
    }

    @Bean
    ICategoryDao categoryLuDao(){
        return new CategoryDao(namedParameterJdbcTemplate);
    }

    @Bean
    ISubcategoryDao subcategoryLuDao(){
        return new SubcategoryDao(namedParameterJdbcTemplate);
    }

}
