package com.dao;

import com.dto.Category;

import java.util.List;

public interface ICategoryDao {
    List<Category> findCategoriesForUser(String userId);
}
