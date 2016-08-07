package com.dao;

import com.dto.Category;

import java.util.Date;
import java.util.List;

public interface ICategoryDao {

    List<Category> getCategories(String budgetId);

    Category lookupCategory(String categoryId);

    int addCategory(String categoryName, String limit, String expenses, Date startDate, Date endDate, String budgetId);

    int updateCategory(String categoryId, String categoryName, String limit, String expenses, Date startDate, Date endDate);

    int deleteCategoryByParentId(String parentId);

    int deleteCategory(String categoryId);

}
