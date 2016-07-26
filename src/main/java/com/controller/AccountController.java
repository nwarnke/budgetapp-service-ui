package com.controller;

import com.dto.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@CrossOrigin(origins = {"http://localhost:9000", "https://budget-management-app.herokuapp.com", "http://budget-management-app.herokuapp.com"})
@RequestMapping("/account")
public class AccountController {

    @RequestMapping(value = "/userinfo", method = RequestMethod.GET)
    @ResponseBody
    public User getUserInfo(HttpServletRequest httpServletRequest){
//        return (User) httpServletRequest.getSession().getAttribute("userInfo");
        return null; //TODO finish method
    }

}
