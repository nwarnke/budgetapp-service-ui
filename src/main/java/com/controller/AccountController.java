package com.controller;

import com.dto.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.dao.IUserDao;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@Controller
@CrossOrigin(origins = {"http://localhost:9000", "https://budget-management-app.herokuapp.com", "http://budget-management-app.herokuapp.com"})
@RequestMapping("/account")
public class AccountController {
    private IUserDao userDao;

    @Inject
    public AccountController(IUserDao userDao) {

        this.userDao = userDao;
    }

    @RequestMapping(value = "/userinfo", method = RequestMethod.GET)
    @ResponseBody
    public User getUserInfo(HttpServletRequest httpServletRequest){
//        return (User) httpServletRequest.getSession().getAttribute("userInfo");
        return null; //TODO finish method
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity updateUserInfo(HttpServletRequest httpServletRequest,
                               @RequestParam("username") String username,
                               @RequestParam("newPassword") String newPassword,
                               @RequestParam("oldPassword") String oldPassword){
       boolean updated = userDao.updateUserInfo(username,newPassword,oldPassword);
        if(updated) {
            return new ResponseEntity(HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
