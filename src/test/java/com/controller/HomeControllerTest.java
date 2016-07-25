package com.controller;

import com.dao.IBudgetDao;
import com.dao.impl.BudgetDao;
import com.dto.UserDto;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

public class HomeControllerTest {

  private Mockery context;
  private HomeController homeController;
  private IBudgetDao budgetDao;

  @Before
  public void setUp() throws Exception {
    context = new Mockery();
    context.setImposteriser(ClassImposteriser.INSTANCE);
    budgetDao = context.mock(BudgetDao.class);
    homeController = new HomeController(budgetDao);
  }

  @Test
  public void getBudgets() throws Exception {
    MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
    mockHttpServletRequest.getSession().setAttribute("authenticated", true);
    final UserDto userDto = createUserDto();
    mockHttpServletRequest.getSession().setAttribute("userInfo", userDto);

    context.checking(new Expectations(){
      {
        oneOf(budgetDao).getBudgets(with(userDto.getUserId()));
      }
    });

    homeController.getBudgets(mockHttpServletRequest);
    context.assertIsSatisfied();
  }

  private UserDto createUserDto() {
    final UserDto userDto = new UserDto();
    userDto.setPassword("password");
    userDto.setUserId("test");
    userDto.setFirstName("john");
    userDto.setLastName("smith");
    userDto.setUserName("testing");
    return userDto;
  }


}