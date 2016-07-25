package com.controller;

import com.dao.IBudgetDao;
import com.dao.ICategoryDao;
import com.dao.impl.BudgetDao;
import com.dao.impl.CategoryDao;
import com.dto.Budget;
import com.dto.UserDto;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class BudgetControllerTest {

  private Mockery context;
  private BudgetController budgetController;
  private IBudgetDao budgetDao;
  private ICategoryDao categoryDao;

  @Before
  public void setUp() throws Exception {
    context = new Mockery();
    context.setImposteriser(ClassImposteriser.INSTANCE);
    budgetDao = context.mock(BudgetDao.class);
    categoryDao = context.mock(CategoryDao.class);
    budgetController = new BudgetController(budgetDao, categoryDao);
  }

  @Test
  public void testGetBudget() throws Exception {
    MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
    mockHttpServletRequest.getSession().setAttribute("authenticated", true);
    final UserDto userDto = createUserDto();
    mockHttpServletRequest.getSession().setAttribute("userInfo", userDto);
    final String budgetId = "12345";
    final Budget budget = createBudget();

    context.checking(new Expectations() {
      {
        oneOf(budgetDao).lookupBudget(with(budgetId), with(userDto.getUserId()));
        will(returnValue(budget));
      }
    });

    final ResponseEntity<Budget> responseEntity = budgetController.getBudget(mockHttpServletRequest, budgetId);
    assertEquals(200, responseEntity.getStatusCodeValue());
    context.assertIsSatisfied();
  }

  @Test
  public void testNewBudget() throws Exception {
    MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
    mockHttpServletRequest.getSession().setAttribute("authenticated", true);
    final UserDto userDto = createUserDto();
    mockHttpServletRequest.getSession().setAttribute("userInfo", userDto);

    final String budgetName = "Test budget";
    final String amount = "1600";
    final String startDate = "2016-07-06T05:00:00.000Z";
    final String endDate = "2016-07-27T05:00:00.000Z";

    context.checking(new Expectations(){
      {
        oneOf(budgetDao).addBudget(with(any(String.class)), with(any(String.class)), with(any(Date.class)), with(any(Date.class)), with(any(String.class)));
        will(returnValue(1));
      }
    });


    final ResponseEntity responseEntity = budgetController.newBudget(mockHttpServletRequest, budgetName, amount, startDate, endDate);
    assertEquals(202, responseEntity.getStatusCodeValue());
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

  private Budget createBudget() {
    final Budget budget = new Budget();
    budget.setBudgetId(12345);
    budget.setCurrentAmount(1500);
    budget.setStartDate(new Date());
    budget.setEndDate(new Date());
    budget.setLimit(2000);
    return budget;
  }
}