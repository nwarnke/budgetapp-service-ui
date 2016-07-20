package com.controller;

import com.dao.IAccountDao;
import com.dto.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@CrossOrigin(origins = {"http://localhost:9000", "https://budget-management-app.herokuapp.com"})
@RequestMapping("/account")
public class AccountController {

    private IAccountDao accountDao;

    @Inject
    public AccountController(IAccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @RequestMapping(value = "/userinfo", method = RequestMethod.GET)
    @ResponseBody
    public UserDto getUserInfo(HttpServletRequest httpServletRequest){
        UserDto userDto = new UserDto();
        userDto.setUserid(httpServletRequest.getSession().getAttribute("userid").toString());
        userDto.setName(httpServletRequest.getSession().getAttribute("username").toString());
        return userDto;
    }

}
