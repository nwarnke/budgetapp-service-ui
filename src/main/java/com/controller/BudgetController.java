package com.controller;

import com.dao.IUserDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;

@Controller
@RequestMapping("/budget")
public class BudgetController {

    private IUserDao userDao;

    @Inject
    public BudgetController(IUserDao userDao) {
        this.userDao = userDao;
    }





}
