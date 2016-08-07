package com.controller;

import com.dao.IBudgetDao;
import com.dao.ICategoryDao;
import com.dao.ISubcategoryDao;
import com.dto.Budget;
import com.dto.Category;
import com.dto.User;
import com.service.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@CrossOrigin(origins = {"http://localhost:9000", "https://budget-management-app.herokuapp.com", "http://budget-management-app.herokuapp.com"})
@RequestMapping("/home")
public class HomeController {

  private IBudgetDao budgetDao;
    private ICategoryDao categoryDao;
    private ISubcategoryDao subcategoryDao;

  @Inject
    public HomeController(IBudgetDao budgetDao, ICategoryDao categoryDao, ISubcategoryDao subcategoryDao) {
        this.budgetDao = budgetDao;
        this.categoryDao = categoryDao;
        this.subcategoryDao = subcategoryDao;
    }

  @RequestMapping(value="/budgets", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<List<Budget>> getBudgets(HttpServletRequest httpServletRequest){
    if(!Service.isAuthenticatedUser(httpServletRequest)){
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    final User userInfo = (User) httpServletRequest.getSession().getAttribute("userInfo");
    return new ResponseEntity<>(budgetDao.getBudgets(userInfo.getUserId()), HttpStatus.OK);
  }

  @RequestMapping(value = "/addbudget", method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity addBudget(HttpServletRequest httpServletRequest, @RequestParam("budgetName") String budgetName,
                                  @RequestParam("amount") String amount,
                                  @RequestParam("startDate") String startDate,
                                  @RequestParam("endDate") String endDate) throws ParseException {
    if(!Service.isAuthenticatedUser(httpServletRequest)){
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    Date startDateUtil = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
    Date endDateUtil = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);

    final int created = budgetDao.addBudget(budgetName, amount, startDateUtil, endDateUtil, ((User) httpServletRequest.getSession().getAttribute("userInfo")).getUserId());

    if(created == 1) {
      return new ResponseEntity(HttpStatus.ACCEPTED);
    }else{
      return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

    @RequestMapping(value = "/updatebudget", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity updateBudget(HttpServletRequest httpServletRequest, @RequestParam("budgetId") String budgetId,
                                @RequestParam("budgetName") String budgetName,
                                @RequestParam("amount") String amount,
                                @RequestParam("limit") String limit,
                                @RequestParam("startDate") String startDate,
                                @RequestParam("endDate") String endDate) throws ParseException {
        if(!Service.isAuthenticatedUser(httpServletRequest)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Date startDateUtil = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
        Date endDateUtil = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
        final int updated = budgetDao.updateBudget(budgetId, budgetName, amount, limit, startDateUtil, endDateUtil);
        if(updated == 1) {
            return new ResponseEntity(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/deletebudget", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity deleteBudget(HttpServletRequest httpServletRequest, @RequestParam("budgetId") String budgetId) throws ParseException {
        if(!Service.isAuthenticatedUser(httpServletRequest)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        final int deleted = budgetDao.deleteBudget(budgetId);
        if(deleted == 1) {
            List<Category> categories = categoryDao.getCategories(budgetId);
            if(categories != null) {
                final int deletedCategories = categoryDao.deleteCategoryByParentId(budgetId);
                if(deletedCategories == 1) {
                    for(Category category: categories) {
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
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

}
