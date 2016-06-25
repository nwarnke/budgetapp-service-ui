package com.trn;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;
import java.util.Map;

public class UserDao implements IUserDao {
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UserDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Map<String, Object>> getSomething(){
        final List<Map<String, Object>> maps = namedParameterJdbcTemplate.getJdbcOperations().queryForList("select name from \"User\" where userid = 12345");
        return maps;
    }


}
