package com.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@CrossOrigin(origins = {"http://localhost:9000", "https://budget-management-app.herokuapp.com", "http://budget-management-app.herokuapp.com"})
public class LogoutController extends HttpServlet {

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity logout(HttpServletRequest httpServletRequest) {
        if(httpServletRequest.getSession().getAttribute("authenticated") != null){
            httpServletRequest.getSession().setAttribute("authenticated", false);
        }
        if(httpServletRequest.getSession().getAttribute("userInfo") != null){
            httpServletRequest.getSession().setAttribute("userInfo", null);
        }
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

}
