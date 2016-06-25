package com.controller;

import com.dao.IUserDao;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    private IUserDao userDao;

    @Inject
    public MainController(IUserDao userDao) {
        this.userDao = userDao;
    }

    @RequestMapping(value = "something", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>> getSomething(HttpServletRequest httpRequest,
                                                  HttpServletResponse httpResponse) throws IOException {
//
        final List<Map<String, Object>> something = userDao.getSomething();
        return something;
    }

}
