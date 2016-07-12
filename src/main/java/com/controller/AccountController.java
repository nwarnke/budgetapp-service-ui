package com.controller;

import com.dao.IAccountDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;

@Controller
@RequestMapping("/account")
public class AccountController {


    private IAccountDao accountDao;

    @Inject
    public AccountController(IAccountDao accountDao) {
        this.accountDao = accountDao;
    }

}
