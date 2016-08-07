package com.controller;

import com.dao.IBudgetDao;
import com.dao.ICategoryDao;
import com.dao.ISubcategoryDao;
import com.dto.Budget;
import com.dto.Category;
import com.dto.User;
import com.service.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@CrossOrigin(origins = {"http://localhost:9000", "https://budget-management-app.herokuapp.com", "http://budget-management-app.herokuapp.com"})
@RequestMapping("/account")
public class AccountController {

    private IBudgetDao budgetDao;
    private ICategoryDao categoryDao;
    private ISubcategoryDao subcategoryDao;

    @Inject
    public AccountController(IBudgetDao budgetDao, ICategoryDao categoryDao, ISubcategoryDao subcategoryDao) {
        this.budgetDao = budgetDao;
        this.categoryDao = categoryDao;
        this.subcategoryDao = subcategoryDao;
    }

    @RequestMapping(value = "/userinfo", method = RequestMethod.GET)
    @ResponseBody
    public User getUserInfo(HttpServletRequest httpServletRequest){
//        return (User) httpServletRequest.getSession().getAttribute("userInfo");
        return null; //TODO finish method
    }

    @RequestMapping(value="/updateUser", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity updateUserCredentials(HttpServletRequest request){
        if(!Service.isAuthenticatedUser(request)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return null; //TODO in the process of implementing method -- Nick
    }

    @RequestMapping(value="/deleteUser", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity deleteUserCredentials(HttpServletRequest request, @RequestParam("parentId") String parentId){
        if(!Service.isAuthenticatedUser(request)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        List<Budget> budgets = budgetDao.getBudgets(parentId);
        if (budgets != null) {
            final int deleted = budgetDao.deleteBudgetByParentId(parentId);
            for (Budget budget : budgets) {
                if (deleted == 1) {
                    List<Category> categories = categoryDao.getCategories(String.valueOf(budget.getBudgetId()));
                    if (categories != null) {
                        final int deletedCategories = categoryDao.deleteCategoryByParentId(String.valueOf(budget.getBudgetId()));
                        if (deletedCategories == 1) {
                            for (Category category : categories) {
                                final int deletedSubCategories = subcategoryDao.deleteSubcategoryByParentId(String.valueOf(category.getCategoryId()));
                                if (deletedSubCategories != 1) {
                                    return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
                                }
                            }
                        } else {
                            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
                        }
                    }
                } else {
                    return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

}
