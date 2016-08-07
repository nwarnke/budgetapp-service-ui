package com.dao.impl;


import com.dao.ISubcategoryDao;
import com.dto.Subcategory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class SubcategoryDao implements ISubcategoryDao {
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public SubcategoryDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Subcategory> getSubCategories(String categoryId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("categoryId", Integer.valueOf(categoryId));
        return namedParameterJdbcTemplate.query("SELECT * FROM subcategory s WHERE s.parent_id = :categoryId",
                mapSqlParameterSource, rowMapper);
    }

    @Override
    public Subcategory lookupSubcategory(String subcategoryId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("subcategoryId", Integer.valueOf(subcategoryId));
        return namedParameterJdbcTemplate.queryForObject("SELECT * FROM subcategory s " +
                "WHERE s.subcategory_id = :subcategoryId", mapSqlParameterSource, rowMapper);
    }

    @Override
    public int addSubcategory(String subcategoryName, String limit, String expenses, Date startDate, Date endDate, String categoryId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("subcategoryName", subcategoryName);
        mapSqlParameterSource.addValue("limit", Integer.parseInt(limit));
        mapSqlParameterSource.addValue("expenses", Integer.parseInt(expenses));
        mapSqlParameterSource.addValue("startDate", startDate);
        mapSqlParameterSource.addValue("endDate", endDate);
        mapSqlParameterSource.addValue("categoryId", Integer.parseInt(categoryId));
        return namedParameterJdbcTemplate.update("INSERT INTO subcategory (subcategory_id, subcategory_name, subcategory_limit, subcategory_original_date, subcategory_last_date, subcategory_expenses, parent_id) VALUES (((SELECT max" +
                "(subcategory_id) FROM subcategory)+1), :subcategoryName, :limit, :startDate, :endDate, :expenses, :categoryId);", mapSqlParameterSource);
    }

    @Override
    public int updateSubcategory(String subcategoryId, String subcategoryName, String limit, String expenses, Date startDate, Date endDate) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("subcategoryId", Integer.parseInt(subcategoryId));
        mapSqlParameterSource.addValue("subcategoryName", subcategoryName);
        mapSqlParameterSource.addValue("limit", Integer.parseInt(limit));
        mapSqlParameterSource.addValue("expenses", Integer.parseInt(expenses));
        mapSqlParameterSource.addValue("startDate", startDate);
        mapSqlParameterSource.addValue("endDate", endDate);
        return namedParameterJdbcTemplate.update("UPDATE subcategory SET subcategory_name = :subcategoryName, " +
                "subcategory_limit = :limit, subcategory_original_date = :startDate, subcategory_last_date = :endDate, " +
                "subcategory_expenses = :expenses WHERE subcategory_id = :subcategoryId;", mapSqlParameterSource);
    }

    @Override
    public int deleteSubcategoryByParentId(String parentId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("parentId", Integer.parseInt(parentId));
        return namedParameterJdbcTemplate.update("DELETE FROM subcategory WHERE parent_id = :parentId;", mapSqlParameterSource);
    }

    @Override
    public int deleteSubcategory(String subcategoryId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("subcategoryId", Integer.parseInt(subcategoryId));
        return namedParameterJdbcTemplate.update("DELETE FROM subcategory WHERE subcategory_id = :subcategoryId;", mapSqlParameterSource);
    }

    private RowMapper<Subcategory> rowMapper = new RowMapper<Subcategory>() {
        @Override
        public Subcategory mapRow(ResultSet rs, int rowNum) throws SQLException {
            Subcategory subcategory = new Subcategory();
            subcategory.setSubcategoryId(rs.getInt("subcategory_id"));
            subcategory.setSubcategoryName(rs.getString("subcategory_name"));
            subcategory.setSubcategoryLimit(rs.getInt("subcategory_limit"));
            subcategory.setSubcategoryExpenses(rs.getInt("subcategory_expenses"));
            subcategory.setParentId(rs.getInt("parent_id"));
            return subcategory;
        }
    };
}
