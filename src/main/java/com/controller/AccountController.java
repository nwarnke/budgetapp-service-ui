package com.controller;

import com.dao.IUserDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;

@Controller
@RequestMapping("account")
public class AccountController {

    private IUserDao userDao;

    @Inject
    public AccountController(IUserDao userDao) {
        this.userDao = userDao;
    }

}
