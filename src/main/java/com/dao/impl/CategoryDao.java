package com.dao.impl;

import com.dao.ICategoryDao;
import com.dto.Category;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class CategoryDao implements ICategoryDao {
  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  public CategoryDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
  }

  @Override
  public List<Category> findCategoriesForBudgetId(String budgetId) {
    MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
    mapSqlParameterSource.addValue("budgetId", Integer.valueOf(budgetId));
    return namedParameterJdbcTemplate.query("SELECT * from category c " +
            "INNER JOIN budget_categories b ON c.category_id=b.category_id where b.budget_id = :budgetId",
            mapSqlParameterSource, rowMapper);
  }

  private RowMapper<Category> rowMapper = new RowMapper<Category>() {
    @Override
    public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
      Category category = new Category();
      category.setCategoryId(rs.getInt("category_id"));
      category.setCategoryName(rs.getString("category_name"));
      category.setCategoryLimit(rs.getInt("category_limit"));
      category.setCategoryExpenses(rs.getInt("category_expenses"));
      return category;
    }
  };

}