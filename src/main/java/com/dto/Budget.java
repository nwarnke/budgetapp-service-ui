package com.dto;

import java.util.Date;
import java.util.List;

public class Budget {
    private Integer budgetId;
    private String budgetName;
    private Integer budgetExpenses;
    private Integer budgetLimit;
    private List<Category> categories;
    private Date startDate;
    private Date endDate;
    private Integer parentId;

    public Integer getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(Integer budgetId) {
        this.budgetId = budgetId;
    }

    public String getBudgetName() {
        return budgetName;
    }

    public void setBudgetName(String budgetName) {
        this.budgetName = budgetName;
    }

    public Integer getBudgetExpenses() {
        return budgetExpenses;
    }

    public void setBudgetExpenses(Integer budgetExpenses) {
        this.budgetExpenses = budgetExpenses;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
}
