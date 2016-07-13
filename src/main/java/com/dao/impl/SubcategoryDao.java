package com.dao.impl;


import com.dao.ISubcategoryDao;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class SubcategoryDao implements ISubcategoryDao {
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public SubcategoryDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
}
