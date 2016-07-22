package com.dao.impl;

import com.dao.IBudgetDao;
import com.dto.Budget;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
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

    private RowMapper<Budget> rowMapper = new RowMapper<Budget>() {
        @Override
        public Budget mapRow(ResultSet rs, int rowNum) throws SQLException {
            Budget budget = new Budget();
            budget.setBudgetId(rs.getInt("budget_id"));
            budget.setBudgetLimit(rs.getInt("budget_limit"));
            budget.setBudgetName(rs.getString("budget_name"));
            budget.setCreationDate(rs.getDate("creation_date"));
            budget.setCurrentAmount(rs.getInt("current_amount"));
            return budget;
        }
    };

}
