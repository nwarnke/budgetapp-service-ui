package com.controller;

import com.dao.IBudgetDao;
import com.dao.ICategoryDao;
import com.dto.Budget;
import com.dto.Category;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/budget")
public class BudgetController {

    private IBudgetDao budgetDao;
    private ICategoryDao categoryDao;

    @Inject
    public BudgetController(IBudgetDao budgetDao, ICategoryDao categoryDao) {
        this.budgetDao = budgetDao;
        this.categoryDao = categoryDao;
    }

    @RequestMapping(value="/getbudget", method = RequestMethod.GET)
    public Budget getBudget(HttpServletRequest httpServletRequest){
        Budget budget = new Budget();
        final String userid = (String) httpServletRequest.getSession().getAttribute("userid");
        final List<Category> categoriesForUser = categoryDao.findCategoriesForUser(userid);
        budget.setBudgetName("Test budget");
        budget.setCategories(categoriesForUser);
        return budget;
    }



}
