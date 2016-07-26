package com.controller;

import com.dao.IUserDao;
import com.dao.impl.UserDao;
import com.dto.User;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class LoginControllerTest {

  private Mockery context;
  private LoginController loginController;
  private IUserDao userDao;

  @Before
  public void setUp() throws Exception {
    context = new Mockery();
    context.setImposteriser(ClassImposteriser.INSTANCE);
    userDao = context.mock(UserDao.class);
    loginController = new LoginController(userDao);
  }

  @Test
  public void isAuthUser() throws Exception {
    MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
    mockHttpServletRequest.getSession().setAttribute("authenticated", true);
    final String username = "test";
    String password = "password";

    final List<User> users = new ArrayList<>();
    users.add(createUser());

    context.checking(new Expectations(){
      {
        oneOf(userDao).getUserPassword(with(username));
        will(returnValue(users));
      }
    });

    final boolean authUser = loginController.isAuthUser(mockHttpServletRequest, username, password);
    assertTrue(authUser);
    context.assertIsSatisfied();
  }

  private User createUser() {
    User user = new User();
    user.setFirstName("john");
    user.setLastName("doe");
    user.setPassword("password");
    user.setUserId("test");
    user.setUserName("testing");
    return user;
  }


}