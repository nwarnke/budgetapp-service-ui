package com.dao;

import com.dto.Subcategory;

import java.util.List;

public interface ISubcategoryDao {

    List<Subcategory> findSubCategoriesForCategoryId(String categoryId);

}
