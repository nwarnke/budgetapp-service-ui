package com.dao;

import com.dto.Budget;

import java.util.List;

public interface IBudgetDao {

    List<Budget> getBudgets(String userId);
}
