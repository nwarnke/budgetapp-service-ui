package com.controller;

import com.dao.IUserDao;
import com.dto.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    public boolean isAuthUser(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestParam("username") String username,
                              @RequestParam("password") String password) throws IOException {
        final List<UserDto> loginInfo = userDao.getLoginInfo(username, password);
        if(loginInfo.size() == 1){
            httpServletRequest.getSession().setAttribute("userid", loginInfo.get(0).getUserid());
            httpServletRequest.getSession().setAttribute("username", loginInfo.get(0).getName());
            httpServletRequest.getSession().setAttribute("isAuthenticated", true);
            return true;
        }else{
            httpServletRequest.getSession().setAttribute("isAuthenticated", false);
            return false;
        }
    }

    @RequestMapping(value = "/userinfo", method = RequestMethod.GET)
    @ResponseBody
    public UserDto getUserInfo(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        UserDto userDto = new UserDto();
        userDto.setUserid(httpServletRequest.getSession().getAttribute("userid").toString());
        userDto.setName(httpServletRequest.getSession().getAttribute("username").toString());
        return userDto;
    }

}
