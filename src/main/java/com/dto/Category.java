package com.dto;

import java.util.List;

public class Category {
    private Integer categoryId;
    private String categoryName;
    private Integer categoryExpenses;
    private Integer categoryLimit;
    private List<Subcategory> subcategories;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getCategoryExpenses() {
        return categoryExpenses;
    }

    public void setCategoryExpenses(Integer categoryExpenses) {
        this.categoryExpenses = categoryExpenses;
    }

    public Integer getCategoryLimit() {
        return categoryLimit;
    }

    public void setCategoryLimit(Integer categoryLimit) {
        this.categoryLimit = categoryLimit;
    }

    public List<Subcategory> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<Subcategory> subcategories) {
        this.subcategories = subcategories;
    }
}
