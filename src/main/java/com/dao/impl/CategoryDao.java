package com.dao.impl;

import com.dao.ICategoryDao;
import com.dto.Category;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CategoryDao implements ICategoryDao {
  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  public CategoryDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
  }

  @Override
  public List<Category> findCategoriesForUser(String userId) {
    MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
    mapSqlParameterSource.addValue("userid", userId);
    return namedParameterJdbcTemplate.query("SELECT b.budget_id, statement_start_date from budget b INNER JOIN statement s on b.budget_id = s.budget_id INNER JOIN category c on " +
            "s.statement_id = c.statement_id where b.user_id = :userid", mapSqlParameterSource, rowMapper);
  }

  private RowMapper<Category> rowMapper = new RowMapper<Category>() {
    @Override
    public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
      Category category = new Category();
      category.setCategoryName(rs.getString("category_name"));
      return category;
    }
  };

}
