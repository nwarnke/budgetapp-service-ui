package com.dao;

import com.dto.Budget;

import java.util.Date;
import java.util.List;

public interface IBudgetDao {

    List<Budget> getBudgets(String userId);

    Budget lookupBudget(String budgetId);

    int addBudget(String budgetName, String amount, Date startDate, Date endDate, String userId);

    int updateBudget(String budgetId,String budgetName, String amount, String limit, Date startDate, Date endDate);

    int deleteBudgetByParentId(String parentId);

    int deleteBudget(String budgetId);
}
