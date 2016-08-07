package com.dao.impl;

import com.dao.IBudgetDao;
import com.dto.Budget;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class BudgetDao implements IBudgetDao {
  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  public BudgetDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
  }

  @Override
  public List<Budget> getBudgets(String userId) {
    MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
    mapSqlParameterSource.addValue("userId", Integer.valueOf(userId));
    return namedParameterJdbcTemplate.query("SELECT * FROM budget b WHERE b.parent_id = :userId;",
            mapSqlParameterSource, rowMapper);

  }

  @Override
  public Budget lookupBudget(String budgetId) {
    MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
    mapSqlParameterSource.addValue("budgetId", Integer.valueOf(budgetId));
    return namedParameterJdbcTemplate.queryForObject("SELECT * FROM budget b " +
            "WHERE b.budget_id = :budgetId", mapSqlParameterSource, rowMapper);
  }

  @Override
  public int addBudget(String budgetName, String limit, Date startDate, Date endDate, String userId) {
    MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
    mapSqlParameterSource.addValue("budgetName", budgetName);
    mapSqlParameterSource.addValue("limit", Integer.parseInt(limit));
    mapSqlParameterSource.addValue("startDate", startDate);
    mapSqlParameterSource.addValue("endDate", endDate);
    mapSqlParameterSource.addValue("userId", Integer.parseInt(userId));
      return namedParameterJdbcTemplate.update("INSERT INTO budget (budget_id, budget_name, current_amount, budget_limit, budget_start_date, budget_end_date, parent_id) " +
              "VALUES (((SELECT max (budget_id) FROM budget)+1), :budgetName, 0, :limit, :startDate, :endDate, :userId);", mapSqlParameterSource);
  }

    @Override
    public int updateBudget(String budgetId, String budgetName, String amount, String limit, Date startDate, Date endDate) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("budgetId", Integer.parseInt(budgetId));
        mapSqlParameterSource.addValue("budgetName", budgetName);
        mapSqlParameterSource.addValue("amount", Integer.parseInt(amount));
        mapSqlParameterSource.addValue("limit", Integer.parseInt(limit));
        mapSqlParameterSource.addValue("startDate", startDate);
        mapSqlParameterSource.addValue("endDate", endDate);
        return namedParameterJdbcTemplate.update("UPDATE budget SET budget_name = :budgetName, current_amount = :amount, " +
                "budget_limit = :limit, budget_start_date = :startDate, budget_end_date = :endDate WHERE budget_id = :budgetId;", mapSqlParameterSource);
    }

    @Override
    public int deleteBudgetByParentId(String parentId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("parentId", Integer.parseInt(parentId));
        return namedParameterJdbcTemplate.update("DELETE FROM budget WHERE parent_id = :parentId;", mapSqlParameterSource);
    }

    @Override
    public int deleteBudget(String budgetId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("budgetId", Integer.parseInt(budgetId));
        return namedParameterJdbcTemplate.update("DELETE FROM budget WHERE budget_id = :budgetId;", mapSqlParameterSource);
    }

  private RowMapper<Budget> rowMapper = new RowMapper<Budget>() {
    @Override
    public Budget mapRow(ResultSet rs, int rowNum) throws SQLException {
      Budget budget = new Budget();
      budget.setBudgetId(rs.getInt("budget_id"));
      budget.setBudgetLimit(rs.getInt("budget_limit"));
      budget.setBudgetExpenses(rs.getInt("current_amount"));
      budget.setBudgetName(rs.getString("budget_name"));
      budget.setStartDate(rs.getDate("budget_start_date"));
      budget.setEndDate(rs.getDate("budget_end_date"));
      budget.setParentId(rs.getInt("parent_id"));
      return budget;
    }
  };

}
