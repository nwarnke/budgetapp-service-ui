package com.service;

import javax.servlet.http.HttpServletRequest;

public class Service {
    public static Boolean isAuthenticatedUser(HttpServletRequest httpServletRequest){
        if(httpServletRequest.getSession().getAttribute("authenticated") != null) {
            return (Boolean) httpServletRequest.getSession().getAttribute("authenticated");
        }else{
            return false;
        }
    }
}
