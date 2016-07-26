package com.dao.impl;

import com.dao.IUserDao;
import com.dto.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDao implements IUserDao {
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UserDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<User> getUserPassword(String username){
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("username", username);
        return namedParameterJdbcTemplate.query("select * from app_user where user_id in (select DISTINCT user_id from app_user where user_name = :username)", mapSqlParameterSource, rowMapper);
    }

    RowMapper<User> rowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setUserName(rs.getString("user_name"));
            if(rs.getString("first_name") != null) {
                user.setFirstName(rs.getString("first_name"));
            }
            if(rs.getString("last_name") != null) {
                user.setLastName(rs.getString("last_name"));
            }
            user.setUserId(rs.getString("user_id"));
            user.setPassword(rs.getString("user_password"));
            return user;
        }
    };

}
