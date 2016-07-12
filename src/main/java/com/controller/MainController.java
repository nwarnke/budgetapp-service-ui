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
    public boolean isAuthUser(HttpServletRequest httpServletRequest,
                              HttpServletResponse httpServletResponse, @RequestParam("username") String username,
                              @RequestParam("password") String password) throws IOException {
        httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
        final List<UserDto> loginInfo = userDao.getLoginInfo(username, password);
        if(loginInfo.size() == 1){
            httpServletRequest.getSession().setAttribute("userid", loginInfo.get(0).getUserid());
            httpServletRequest.getSession().setAttribute("username", loginInfo.get(0).getName());
            return true;
        }else{
            return false;
        }
    }

    @RequestMapping(value = "userinfo", method = RequestMethod.GET)
    @ResponseBody
    public UserDto getUserInfo(HttpServletRequest httpServletRequest){
        UserDto userDto = new UserDto();
        userDto.setUserid(httpServletRequest.getSession().getAttribute("userid").toString());
        userDto.setName(httpServletRequest.getSession().getAttribute("username").toString());
        return userDto;
    }

}
