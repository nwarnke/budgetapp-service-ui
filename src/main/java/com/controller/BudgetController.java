package com.controller;

import com.dao.IBudgetDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;

@Controller
@RequestMapping("/budget")
public class BudgetController {

    private IBudgetDao budgetDao;

    @Inject
    public BudgetController(IBudgetDao budgetDao) {
        this.budgetDao = budgetDao;
    }





}
