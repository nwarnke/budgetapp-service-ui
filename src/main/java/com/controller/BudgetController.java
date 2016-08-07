package com.controller;

import com.dao.IBudgetDao;
import com.dao.ICategoryDao;
import com.dao.ISubcategoryDao;
import com.dto.Budget;
import com.dto.Category;
import com.dto.Subcategory;
import com.service.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@CrossOrigin(origins = {"http://localhost:9000", "https://budget-management-app.herokuapp.com", "http://budget-management-app.herokuapp.com"})
@RequestMapping("/budget")
public class BudgetController {

    private IBudgetDao budgetDao;
    private ICategoryDao categoryDao;
    private ISubcategoryDao subcategoryDao;

    @Inject
    public BudgetController(IBudgetDao budgetDao, ICategoryDao categoryDao, ISubcategoryDao subcategoryDao) {
        this.budgetDao = budgetDao;
        this.categoryDao = categoryDao;
        this.subcategoryDao = subcategoryDao;
    }

    @RequestMapping(value = "/getbudget", method = RequestMethod.GET)
    public ResponseEntity<Budget> getBudget(HttpServletRequest httpServletRequest,
                                            @RequestParam("budgetId") String budgetId) {
        if(!Service.isAuthenticatedUser(httpServletRequest)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        final Budget budget = budgetDao.lookupBudget(budgetId);
        List<Category> categories = categoryDao.getCategories(budgetId);
        if(categories == null) {
            categories = new ArrayList<>();
        }
        budget.setCategories(categories);
        for(Category category: budget.getCategories()){
            String categoryId = String.valueOf(category.getCategoryId());
            List<Subcategory> subcategories = subcategoryDao.getSubCategories(categoryId);
            if(subcategories == null) {
                subcategories = new ArrayList<>();
            }
            category.setSubcategories(subcategories);
        }
        return new ResponseEntity<>(budget, HttpStatus.OK);
    }

    @RequestMapping(value = "/addcategory", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity addCategory(HttpServletRequest httpServletRequest, @RequestParam("categoryName") String categoryName,
                                    @RequestParam("limit") String limit,
                                    @RequestParam("expenses") String expenses,
                                    @RequestParam("startDate") String startDate,
                                    @RequestParam("endDate") String endDate,
                                    @RequestParam("budgetId") String budgetId) throws ParseException {
        if(!Service.isAuthenticatedUser(httpServletRequest)){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Date startDateUtil = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
        Date endDateUtil = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);

        final int created = categoryDao.addCategory(categoryName, limit, expenses, startDateUtil, endDateUtil, budgetId);

        if(created == 1) {
            return new ResponseEntity(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/addsubcategory", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity addSubcategory(HttpServletRequest httpServletRequest, @RequestParam("subcategoryName") String subcategoryName,
                                         @RequestParam("limit") String limit,
                                         @RequestParam("expenses") String expenses,
                                         @RequestParam("startDate") String startDate,
                                         @RequestParam("endDate") String endDate,
                                         @RequestParam("categoryId") String categoryId) throws ParseException {
        if(!Service.isAuthenticatedUser(httpServletRequest)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Date startDateUtil = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
        Date endDateUtil = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);

        final int created = subcategoryDao.addSubcategory(subcategoryName, limit, expenses, startDateUtil, endDateUtil, categoryId);

        if(created == 1) {
            return new ResponseEntity(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/updatecategory", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity updateCategory(HttpServletRequest httpServletRequest, @RequestParam("categoryId") String categoryId,
                                         @RequestParam("categoryName") String categoryName,
                                         @RequestParam("limit") String limit,
                                         @RequestParam("expenses") String expenses,
                                         @RequestParam("startDate") String startDate,
                                         @RequestParam("endDate") String endDate) throws ParseException {
        if(!Service.isAuthenticatedUser(httpServletRequest)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Date startDateUtil = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
        Date endDateUtil = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);

        final int updated = categoryDao.updateCategory(categoryId, categoryName, limit, expenses, startDateUtil, endDateUtil);

        if(updated == 1) {
            return new ResponseEntity(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/updatesubcategory", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity updateSubcategory(HttpServletRequest httpServletRequest, @RequestParam("subcategoryId") String subcategoryId,
                                         @RequestParam("subcategoryName") String subcategoryName,
                                         @RequestParam("limit") String limit,
                                         @RequestParam("expenses") String expenses,
                                         @RequestParam("startDate") String startDate,
                                         @RequestParam("endDate") String endDate) throws ParseException {
        if(!Service.isAuthenticatedUser(httpServletRequest)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Date startDateUtil = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
        Date endDateUtil = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);

        final int updated = subcategoryDao.updateSubcategory(subcategoryId, subcategoryName, limit, expenses, startDateUtil, endDateUtil);

        if(updated == 1) {
            return new ResponseEntity(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/deletecategory", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity deleteCategory(HttpServletRequest httpServletRequest, @RequestParam("categoryId") String categoryId) throws ParseException {
        if(!Service.isAuthenticatedUser(httpServletRequest)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        final int deleted = categoryDao.deleteCategory(categoryId);
        if(deleted == 1) {
            final int deletedSubCategories = subcategoryDao.deleteSubcategoryByParentId(categoryId);
            if(deletedSubCategories == 1){
                return new ResponseEntity(HttpStatus.ACCEPTED);
            }
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/deletesubcategory", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity deleteSubcategory(HttpServletRequest httpServletRequest, @RequestParam("subcategoryId") String subcategoryId) throws ParseException {
        if(!Service.isAuthenticatedUser(httpServletRequest)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        final int deleted = subcategoryDao.deleteSubcategory(subcategoryId);
        if(deleted == 1) {
            return new ResponseEntity(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}