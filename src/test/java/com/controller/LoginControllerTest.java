package com.controller;

import com.dao.IUserDao;
import com.dao.impl.UserDao;
import com.dto.UserDto;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.ArrayList;
import java.util.List;

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

    final List<UserDto> userDtos = new ArrayList<>();
    userDtos.add(createUserDto());

    context.checking(new Expectations(){
      {
        oneOf(userDao).getUserPassword(with(username));
        will(returnValue(userDtos));
      }
    });

    loginController.isAuthUser(mockHttpServletRequest,username, password);
  }

  private UserDto createUserDto() {
    UserDto userDto = new UserDto();
    userDto.setFirstName("john");
    userDto.setLastName("doe");
    userDto.setPassword("password");
    userDto.setUserId("test");
    userDto.setUserName("testing");
    return userDto;
  }


}