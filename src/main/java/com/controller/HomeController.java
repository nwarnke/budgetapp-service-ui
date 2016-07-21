package com.controller;

import com.dao.IBudgetDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@Controller
@CrossOrigin(origins = {"http://localhost:9000", "https://budget-management-app.herokuapp.com"})
@RequestMapping("/home")
public class HomeController {

  private IBudgetDao budgetDao;

  @Inject
  public HomeController(IBudgetDao budgetDao) {
    this.budgetDao = budgetDao;
  }

  @RequestMapping(value="/authenticated", method = RequestMethod.GET)
  @ResponseBody
  public boolean isAuthenticated(HttpServletRequest httpServletRequest){
    if(httpServletRequest.getSession().getAttribute("authenticated") != null) {
      return (Boolean) httpServletRequest.getSession().getAttribute("authenticated");
    }else{
      return false;
    }
  }

}
