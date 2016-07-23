package com.dao;

import com.dto.Budget;

import java.util.Date;
import java.util.List;

public interface IBudgetDao {

    List<Budget> getBudgets(String userId);

    int addBudget(String budgetName, String amount, Date startDate, Date endDate, String userId);
}
