package com.dao.impl;


import com.dao.ISubcategoryDao;
import com.dto.Subcategory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SubcategoryDao implements ISubcategoryDao {
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public SubcategoryDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Subcategory> findSubCategoriesForCategoryId(String categoryId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("categoryId", Integer.valueOf(categoryId));
        return namedParameterJdbcTemplate.query("SELECT * from subcategory s " +
                "INNER JOIN category_subcategories c ON s.subcategory_id=c.subcategory_id where c.category_id = :categoryId",
                mapSqlParameterSource, rowMapper);
    }

    private RowMapper<Subcategory> rowMapper = new RowMapper<Subcategory>() {
        @Override
        public Subcategory mapRow(ResultSet rs, int rowNum) throws SQLException {
            Subcategory subcategory = new Subcategory();
            subcategory.setSubcategoryId(rs.getInt("subcategory_id"));
            subcategory.setSubcategoryName(rs.getString("subcategory_name"));
            subcategory.setSubcategoryLimit(rs.getInt("subcategory_limit"));
            subcategory.setSubcategoryExpenses(rs.getInt("subcategory_expenses"));
            return subcategory;
        }
    };
}
