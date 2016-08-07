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
  public List<Category> getCategories(String budgetId) {
    MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
    mapSqlParameterSource.addValue("budgetId", Integer.valueOf(budgetId));
    return namedParameterJdbcTemplate.query("SELECT * FROM category c  WHERE c.parent_id = :budgetId",
            mapSqlParameterSource, rowMapper);
  }

    @Override
    public Category lookupCategory(String categoryId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("categoryId", Integer.valueOf(categoryId));
        return namedParameterJdbcTemplate.queryForObject("SELECT * FROM category c " +
                "WHERE c.category_id = :categoryId", mapSqlParameterSource, rowMapper);
    }

    @Override
    public int addCategory(String categoryName, String limit, String expenses, Date startDate, Date endDate, String budgetId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("categoryName", categoryName);
        mapSqlParameterSource.addValue("limit", Integer.parseInt(limit));
        mapSqlParameterSource.addValue("expenses", Integer.parseInt(expenses));
        mapSqlParameterSource.addValue("startDate", startDate);
        mapSqlParameterSource.addValue("endDate", endDate);
        mapSqlParameterSource.addValue("budgetId", Integer.parseInt(budgetId));
        return namedParameterJdbcTemplate.update("INSERT INTO category (category_id, category_name, category_limit, category_original_date, category_last_date, category_expenses, parent_id) " +
                "VALUES (((SELECT max (category_id) FROM category)+1), :categoryName, :limit, :startDate, :endDate, :expenses, :budgetId);", mapSqlParameterSource);
    }

    @Override
    public int updateCategory(String categoryId, String categoryName, String limit, String expenses, Date startDate, Date endDate) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("categoryId", Integer.parseInt(categoryId));
        mapSqlParameterSource.addValue("categoryName", categoryName);
        mapSqlParameterSource.addValue("limit", Integer.parseInt(limit));
        mapSqlParameterSource.addValue("expenses", Integer.parseInt(expenses));
        mapSqlParameterSource.addValue("startDate", startDate);
        mapSqlParameterSource.addValue("endDate", endDate);
        return namedParameterJdbcTemplate.update("UPDATE category SET category_name = :categoryName, category_limit = :limit, " +
                "category_original_date = :startDate, category_last_date = :endDate, " +
                "category_expenses = :expenses WHERE category_id = :categoryId;", mapSqlParameterSource);
    }

    @Override
    public int deleteCategoryByParentId(String parentId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("parentId", Integer.parseInt(parentId));
        return namedParameterJdbcTemplate.update("DELETE FROM category WHERE parent_id = :parentId;", mapSqlParameterSource);
    }

    @Override
    public int deleteCategory(String categoryId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("categoryId", Integer.parseInt(categoryId));
        return namedParameterJdbcTemplate.update("DELETE FROM category WHERE category_id = :categoryId;", mapSqlParameterSource);
    }

    private RowMapper<Category> rowMapper = new RowMapper<Category>() {
        @Override
        public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
            Category category = new Category();
            category.setCategoryId(rs.getInt("category_id"));
            category.setCategoryName(rs.getString("category_name"));
            category.setCategoryLimit(rs.getInt("category_limit"));
            category.setCategoryExpenses(rs.getInt("category_expenses"));
            category.setParentId(rs.getInt("parent_id"));
            return category;
        }
    };
}