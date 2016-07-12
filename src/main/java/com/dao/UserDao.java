package com.dao;

import com.dto.UserDto;
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
    public List<UserDto> getLoginInfo(String name, String password){
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("username", name);
        mapSqlParameterSource.addValue("password", password);
        return namedParameterJdbcTemplate.query("select * from \"user\" where user_name = :username and user_password = :password", mapSqlParameterSource, rowMapper);
    }

    RowMapper<UserDto> rowMapper = new RowMapper<UserDto>() {
        @Override
        public UserDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserDto userDto = new UserDto();
            userDto.setName(rs.getString("user_name"));
            userDto.setUserid(rs.getString("user_id"));
            return userDto;
        }
    };

}
