package com.dao.impl;

import com.dao.IAccountDao;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class AccountDao implements IAccountDao {
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public AccountDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
}
