package com.controller;

import com.dto.User;
import com.service.Service;
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
    public User getUserInfo(HttpServletRequest httpServletRequest) {
//        return (User) httpServletRequest.getSession().getAttribute("userInfo");
        return null;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity updateUserInfo(HttpServletRequest httpServletRequest,
                                         @RequestParam("newPassword") String newPassword,
                                         @RequestParam("oldPassword") String oldPassword) {
        if (!Service.isAuthenticatedUser(httpServletRequest)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        boolean updated = userDao.updateUserInfo(((User) httpServletRequest.getSession().getAttribute("userInfo")).getUserName(), newPassword, oldPassword);
        if (updated) {
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity saveUserInfo(HttpServletRequest httpServletRequest,
                                       @RequestParam("firstname") String firstname,
                                       @RequestParam("lastname") String lastname,
                                       @RequestParam("username") String username,
                                       @RequestParam("userid") int userid) {
        if (!Service.isAuthenticatedUser(httpServletRequest)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        boolean updated = userDao.saveUserInfo(firstname, lastname, username, userid);
        if (updated) {
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
