package com.dto;

import java.util.Date;
import java.util.List;

public class Budget {
    private Integer budgetId;
    private String budgetName;
    private List<Category> categories;
    private Integer budgetLimit;
    private Integer currentAmount;
    private Date creationDate;

    public Integer getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(Integer currentAmount) {
        this.currentAmount = currentAmount;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(Integer budgetId) {
        this.budgetId = budgetId;
    }

    public Integer getBudgetLimit() {
        return budgetLimit;
    }

    public void setBudgetLimit(Integer budgetLimit) {
        this.budgetLimit = budgetLimit;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public String getBudgetName() {
        return budgetName;
    }

    public void setBudgetName(String budgetName) {
        this.budgetName = budgetName;
    }
}
