package com.controller;

import com.dao.IBudgetDao;
import com.dao.impl.BudgetDao;
import com.dto.Budget;
import com.dto.User;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.List;

import static org.junit.Assert.assertEquals;

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
    final User user = createUser();
    mockHttpServletRequest.getSession().setAttribute("userInfo", user);

    context.checking(new Expectations(){
      {
        oneOf(budgetDao).getBudgets(with(user.getUserId()));
      }
    });

    final ResponseEntity<List<Budget>> responseEntity = homeController.getBudgets(mockHttpServletRequest);
    assertEquals(200, responseEntity.getStatusCodeValue());
    context.assertIsSatisfied();
  }

  private User createUser() {
    final User user = new User();
    user.setPassword("password");
    user.setUserId("test");
    user.setFirstName("john");
    user.setLastName("smith");
    user.setUserName("testing");
    return user;
  }


}