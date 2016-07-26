package com.controller;

import com.dao.IBudgetDao;
import com.dto.Budget;
import com.dto.User;
import com.service.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@CrossOrigin(origins = {"http://localhost:9000", "https://budget-management-app.herokuapp.com", "http://budget-management-app.herokuapp.com"})
@RequestMapping("/home")
public class HomeController {

  private IBudgetDao budgetDao;

  @Inject
  public HomeController(IBudgetDao budgetDao) {
    this.budgetDao = budgetDao;
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

}
