package com.dao.impl;

import com.dao.IBudgetDao;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class BudgetDao implements IBudgetDao {
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public BudgetDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
}
