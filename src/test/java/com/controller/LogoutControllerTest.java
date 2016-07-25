package com.controller;

import com.dto.UserDto;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.Assert.assertEquals;

public class LogoutControllerTest {

  private LogoutController logoutController;

  @Before
  public void setUp(){
    logoutController = new LogoutController();
  }

  @Test
  public void logout() throws Exception {
    MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
    mockHttpServletRequest.getSession().setAttribute("authenticated", true);
    mockHttpServletRequest.getSession().setAttribute("userInfo", new UserDto());
    final ResponseEntity logout = logoutController.logout(mockHttpServletRequest);
    assertEquals(logout.getStatusCode().value(), 202);
  }

}