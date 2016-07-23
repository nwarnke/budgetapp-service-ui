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
    public List<Budget> getBudgets(String userId){
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("userid", Integer.valueOf(userId));
        return namedParameterJdbcTemplate.query("select * from budget where budget_id in (select budget_id from user_budgets where user_id = :userid);", mapSqlParameterSource, rowMapper);

    }

    @Override
    public int addBudget(String budgetName, String amount, Date startDate, Date endDate, String userId){
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("budgetname", budgetName);
        mapSqlParameterSource.addValue("amount", Integer.parseInt(amount));
        mapSqlParameterSource.addValue("startdate", startDate);
        mapSqlParameterSource.addValue("enddate", endDate);
        mapSqlParameterSource.addValue("userid", Integer.parseInt(userId));
        final int update = namedParameterJdbcTemplate.update("insert into budget (budget_id, budget_name, budget_limit, budget_start_date, budget_end_date) values (((select max(budget_id) from budget)+1), :budgetname, :amount, :startdate, :enddate); insert into user_budgets (user_id, budget_id) VALUES (:userid, (select max(budget_id) from budget))", mapSqlParameterSource);
        return update;
    }


    private RowMapper<Budget> rowMapper = new RowMapper<Budget>() {
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
