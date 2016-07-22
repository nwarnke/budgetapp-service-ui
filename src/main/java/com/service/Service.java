package com.service;

import javax.servlet.http.HttpServletRequest;

public class Service {
    public static Boolean isAuthenticatedUser(HttpServletRequest httpServletRequest){
        return (Boolean) httpServletRequest.getSession().getAttribute("authenticated");
    }
}
