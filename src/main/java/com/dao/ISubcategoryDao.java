package com.dao;

import com.dto.Subcategory;

import java.util.Date;
import java.util.List;

public interface ISubcategoryDao {

    List<Subcategory> getSubCategories(String categoryId);

    Subcategory lookupSubcategory(String subcategoryId);

    int addSubcategory(String subcategoryName, String limit, String expenses, Date startDate, Date endDate, String categoryId);

    int updateSubcategory(String subcategoryId, String subcategoryName, String limit, String expenses, Date startDate, Date endDate);

    int deleteSubcategoryByParentId(String parentId);

    int deleteSubcategory(String subcategoryId);
}
