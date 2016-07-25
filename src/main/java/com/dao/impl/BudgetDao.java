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
    mapSqlParameterSource.addValue("userid", Integer.valueOf(userId));
    return namedParameterJdbcTemplate.query("SELECT * FROM budget WHERE budget_id IN (SELECT budget_id FROM user_budgets WHERE user_id = :userid);", mapSqlParameterSource, budget);

  }

  @Override
  public int addBudget(String budgetName, String amount, Date startDate, Date endDate, String userId) {
    MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
    mapSqlParameterSource.addValue("budgetname", budgetName);
    mapSqlParameterSource.addValue("amount", Integer.parseInt(amount));
    mapSqlParameterSource.addValue("startdate", startDate);
    mapSqlParameterSource.addValue("enddate", endDate);
    mapSqlParameterSource.addValue("userid", Integer.parseInt(userId));
    final int update = namedParameterJdbcTemplate.update("INSERT INTO budget (budget_id, budget_name, budget_limit, budget_start_date, budget_end_date) VALUES (((SELECT max" +
            "(budget_id) FROM budget)+1), :budgetname, :amount, :startdate, :enddate); INSERT INTO user_budgets (user_id, budget_id) VALUES (:userid, (SELECT max(budget_id) FROM" +
            " budget))", mapSqlParameterSource);
    return update;
  }

  @Override
  public Budget lookupBudget(String userId, String budgetId) {
    MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
    mapSqlParameterSource.addValue("userid", Integer.valueOf(userId));
    mapSqlParameterSource.addValue("budgetid", Integer.valueOf(budgetId));
    return namedParameterJdbcTemplate.queryForObject("SELECT * FROM budget WHERE budget_id IN (SELECT budget_id FROM user_budgets WHERE user_id = :userid AND user_budgets" +
            ".budget_id = :budgetid)", mapSqlParameterSource, budget);
  }


  private RowMapper<Budget> budget = new RowMapper<Budget>() {
    @Override
    public Budget mapRow(ResultSet rs, int rowNum) throws SQLException {
      Budget budget = new Budget();
      budget.setBudgetId(rs.getInt("budget_id"));
      budget.setLimit(rs.getInt("budget_limit"));
      budget.setCurrentAmount(rs.getInt("current_amount"));
      budget.setName(rs.getString("budget_name"));
      budget.setStartDate(rs.getDate("budget_start_date"));
      budget.setEndDate(rs.getDate("budget_end_date"));
      return budget;
    }
  };

}
