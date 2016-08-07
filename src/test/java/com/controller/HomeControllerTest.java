package com.controller;

import com.dao.IBudgetDao;
import com.dao.ICategoryDao;
import com.dao.ISubcategoryDao;
import com.dao.impl.BudgetDao;
import com.dao.impl.CategoryDao;
import com.dao.impl.SubcategoryDao;
import com.dto.Budget;
import com.dto.Category;
import com.dto.User;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class HomeControllerTest {

  private Mockery context;
  private HomeController homeController;
  private IBudgetDao budgetDao;
  private ICategoryDao categoryDao;
  private ISubcategoryDao subcategoryDao;

  @Before
  public void setUp() throws Exception {
    context = new Mockery();
    context.setImposteriser(ClassImposteriser.INSTANCE);
    budgetDao = context.mock(BudgetDao.class);
    categoryDao = context.mock(CategoryDao.class);
    subcategoryDao = context.mock(SubcategoryDao.class);
    homeController = new HomeController(budgetDao, categoryDao, subcategoryDao);
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

  @Test
  public void testAddBudget() throws Exception {
    MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
    mockHttpServletRequest.getSession().setAttribute("authenticated", true);
    final User user = createUser();
    mockHttpServletRequest.getSession().setAttribute("userInfo", user);

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

    final ResponseEntity responseEntity = homeController.addBudget(mockHttpServletRequest, budgetName, amount, startDate, endDate);
    assertEquals(202, responseEntity.getStatusCodeValue());
    context.assertIsSatisfied();
  }

    @Test
    public void testUpdateBudget() throws Exception {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.getSession().setAttribute("authenticated", true);

        final String budgetId = "3";
        final String budgetName = "Test budget";
        final String amount = "200";
        final String limit = "1400";
        final String startDate = "2016-07-06T05:00:00.000Z";
        final String endDate = "2016-07-27T05:00:00.000Z";

        context.checking(new Expectations(){
            {
                oneOf(budgetDao).updateBudget(with(any(String.class)), with(any(String.class)), with(any(String.class)), with(any(String.class)), with(any(Date.class)), with(any(Date.class)));
                will(returnValue(1));
            }
        });

        final ResponseEntity responseEntity = homeController.updateBudget(mockHttpServletRequest, budgetId, budgetName, amount, limit, startDate, endDate);
        assertEquals(202, responseEntity.getStatusCodeValue());
        context.assertIsSatisfied();
    }

    @Test
    public void testDeleteBudget() throws Exception {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.getSession().setAttribute("authenticated", true);

        final String budgetId = "3";
        final List<Category> categories = createCategoryList();

        context.checking(new Expectations(){
            {
                oneOf(budgetDao).deleteBudget(with(any(String.class)));
                will(returnValue(1));
                oneOf(categoryDao).getCategories(with(any(String.class)));
                will(returnValue(categories));
                oneOf(categoryDao).deleteCategoryByParentId(with(any(String.class)));
                will(returnValue(1));
                oneOf(subcategoryDao).deleteSubcategoryByParentId(with(any(String.class)));
                will(returnValue(1));
                oneOf(subcategoryDao).deleteSubcategoryByParentId(with(any(String.class)));
                will(returnValue(1));
            }
        });

        final ResponseEntity responseEntity = homeController.deleteBudget(mockHttpServletRequest, budgetId);
        assertEquals(202, responseEntity.getStatusCodeValue());
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

    private List<Category> createCategoryList() {
        final List<Category> categories = new ArrayList<>();
        Category category = new Category();
        category.setCategoryId(1);
        categories.add(category);
        Category categorySecond = new Category();
        category.setCategoryId(2);
        categories.add(categorySecond);
        return categories;
    }

}