package com.controller;

import com.dao.IUserDao;
import com.dto.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
@CrossOrigin(origins = {"http://localhost:9000", "https://budget-management-app.herokuapp.com"})
@RequestMapping("/login")
public class LoginController extends HttpServlet {

    private IUserDao userDao;

    @Inject
    public LoginController(IUserDao userDao) {
        this.userDao = userDao;
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    @ResponseBody
    public boolean isAuthUser(HttpServletRequest httpServletRequest, @RequestParam("username") String username,
                              @RequestParam("password") String password) throws IOException {
        final List<UserDto> userDto = userDao.getUserPassword(username);
        if(userDto.size() == 1){
            if(userDto.get(0).getPassword().equals(password)){
                httpServletRequest.getSession().setAttribute("authenticated", true);
                httpServletRequest.getSession().setAttribute("userInfo", userDto.get(0));
                return true;
            }else{
                httpServletRequest.getSession().setAttribute("authenticated", false);
                return false;
            }
        }else{
            httpServletRequest.getSession().setAttribute("authenticated", false);
            return false;
        }

    }



}
