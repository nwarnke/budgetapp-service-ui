package com.controller;

import com.dao.IUserDao;
import com.dto.UserDto;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class MainController {

    private IUserDao userDao;

    @Inject
    public MainController(IUserDao userDao) {
        this.userDao = userDao;
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public List<UserDto> isAuthUser(HttpServletRequest httpRequest,
                                  HttpServletResponse httpResponse, @RequestParam("username") String username,
                                  @RequestParam("password") String password) throws IOException {
        httpResponse.addHeader("Access-Control-Allow-Origin", "*");
        System.out.println("Hello world from Stephanie 2");
        return userDao.getLoginInfo(username, password);
    }

}
