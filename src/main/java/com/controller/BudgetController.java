package com.controller;

import com.dao.IBudgetDao;
import com.dao.ICategoryDao;
import com.dto.Budget;
import com.dto.User;
import com.service.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@CrossOrigin(origins = {"http://localhost:9000", "https://budget-management-app.herokuapp.com", "http://budget-management-app.herokuapp.com"})
@RequestMapping("/budget")
public class BudgetController {

    private IBudgetDao budgetDao;
    private ICategoryDao categoryDao;

    @Inject
    public BudgetController(IBudgetDao budgetDao, ICategoryDao categoryDao) {
        this.budgetDao = budgetDao;
        this.categoryDao = categoryDao;
    }

    @RequestMapping(value = "/getbudget", method = RequestMethod.GET)
    public ResponseEntity<Budget> getBudget(HttpServletRequest httpServletRequest, @RequestParam("budgetId") String budgetId) {     //TODO complete method. Needs database query for categories/subcategories
        if(!Service.isAuthenticatedUser(httpServletRequest)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        final User userInfo = (User) httpServletRequest.getSession().getAttribute("userInfo");
        final Budget budget = budgetDao.lookupBudget(budgetId, userInfo.getUserId());
        return new ResponseEntity<>(budget, HttpStatus.OK);
    }

    @RequestMapping(value = "/newbudget", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity newBudget(HttpServletRequest httpServletRequest, @RequestParam("budgetName") String budgetName,
                                    @RequestParam("amount") String amount,
                                    @RequestParam("startDate") String startDate,
                                    @RequestParam("endDate") String endDate) throws ParseException {
        if(!Service.isAuthenticatedUser(httpServletRequest)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Date startDateUtil = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
        Date endDateUtil = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);

        final int updated = budgetDao.addBudget(budgetName, amount, startDateUtil, endDateUtil, ((User) httpServletRequest.getSession().getAttribute("userInfo")).getUserId());

        if(updated == 1) {
            return new ResponseEntity(HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
