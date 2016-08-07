package com.controller;

import com.dao.IBudgetDao;
import com.dao.ICategoryDao;
import com.dao.ISubcategoryDao;
import com.dao.impl.BudgetDao;
import com.dao.impl.CategoryDao;
import com.dao.impl.SubcategoryDao;
import com.dto.Budget;
import com.dto.Category;
import com.dto.Subcategory;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class BudgetControllerTest {

  private Mockery context;
  private BudgetController budgetController;
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
    budgetController = new BudgetController(budgetDao, categoryDao, subcategoryDao);
  }

  @Test
  public void testGetBudget() throws Exception {
    MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
    mockHttpServletRequest.getSession().setAttribute("authenticated", true);
    final String budgetId = "4";
    final String categoryId = "1";
    final String secondCategoryId = "2";
    final Budget budget = createBudget();
    final List<Category> categories = createCategories();
    final List<Subcategory> subcategories = createSubcategories();

    context.checking(new Expectations() {
      {
        oneOf(budgetDao).lookupBudget(with(budgetId));
        will(returnValue(budget));
        oneOf(categoryDao).getCategories(with(budgetId));
        will(returnValue(categories));
        oneOf(subcategoryDao).getSubCategories(with(categoryId));
        will(returnValue(subcategories));
        oneOf(subcategoryDao).getSubCategories(with(secondCategoryId));
        will(returnValue(subcategories));
      }
    });

    final ResponseEntity<Budget> responseEntity = budgetController.getBudget(mockHttpServletRequest, budgetId);
    assertEquals(200, responseEntity.getStatusCodeValue());
    context.assertIsSatisfied();
  }

  @Test
  public void testAddCategory() throws Exception {
    MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
    mockHttpServletRequest.getSession().setAttribute("authenticated", true);

    final String categoryName = "Test category";
    final String limit = "1600";
    final String expenses = "200";
    final String startDate = "2016-07-06T05:00:00.000Z";
    final String endDate = "2016-07-27T05:00:00.000Z";
    final String budgetId = "1";

    context.checking(new Expectations(){
      {
        oneOf(categoryDao).addCategory(with(any(String.class)), with(any(String.class)), with(any(String.class)), with(any(Date.class)), with(any(Date.class)), with(any(String.class)));
        will(returnValue(1));
      }
    });

    final ResponseEntity responseEntity = budgetController.addCategory(mockHttpServletRequest, categoryName, limit, expenses, startDate, endDate, budgetId);
    assertEquals(202, responseEntity.getStatusCodeValue());
    context.assertIsSatisfied();
  }

    @Test
    public void testAddSubCategory() throws Exception {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.getSession().setAttribute("authenticated", true);

        final String subcategoryName = "Test subcategory";
        final String limit = "400";
        final String expenses = "50";
        final String startDate = "2016-07-06T05:00:00.000Z";
        final String endDate = "2016-07-27T05:00:00.000Z";
        final String categoryId = "12";

        context.checking(new Expectations(){
            {
                oneOf(subcategoryDao).addSubcategory(with(any(String.class)), with(any(String.class)), with(any(String.class)), with(any(Date.class)), with(any(Date.class)), with(any(String.class)));
                will(returnValue(1));
            }
        });

        final ResponseEntity responseEntity = budgetController.addSubcategory(mockHttpServletRequest, subcategoryName, limit, expenses, startDate, endDate, categoryId);
        assertEquals(202, responseEntity.getStatusCodeValue());
        context.assertIsSatisfied();
    }

  @Test
  public void testUpdateCategory() throws Exception {
    MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
    mockHttpServletRequest.getSession().setAttribute("authenticated", true);

    final String categoryId = "12";
    final String categoryName = "Test category update";
    final String amount = "500";
    final String limit = "1000";
    final String startDate = "2016-07-06T05:00:00.000Z";
    final String endDate = "2016-07-27T05:00:00.000Z";

    context.checking(new Expectations(){
        {
            oneOf(categoryDao).updateCategory(with(any(String.class)), with(any(String.class)), with(any(String.class)), with(any(String.class)), with(any(Date.class)), with(any(Date.class)));
            will(returnValue(1));
        }
    });

    final ResponseEntity responseEntity = budgetController.updateCategory(mockHttpServletRequest, categoryId, categoryName, amount, limit, startDate, endDate);
    assertEquals(202, responseEntity.getStatusCodeValue());
    context.assertIsSatisfied();
  }

    @Test
    public void testUpdateSubcategory() throws Exception {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.getSession().setAttribute("authenticated", true);

        final String subcategoryId = "5";
        final String subcategoryName = "Test subcategory update";
        final String amount = "10";
        final String limit = "100";
        final String startDate = "2016-07-06T05:00:00.000Z";
        final String endDate = "2016-07-27T05:00:00.000Z";

        context.checking(new Expectations(){
            {
                oneOf(subcategoryDao).updateSubcategory(with(any(String.class)), with(any(String.class)), with(any(String.class)), with(any(String.class)), with(any(Date.class)), with(any(Date.class)));
                will(returnValue(1));
            }
        });

        final ResponseEntity responseEntity = budgetController.updateSubcategory(mockHttpServletRequest, subcategoryId, subcategoryName, amount, limit, startDate, endDate);
        assertEquals(202, responseEntity.getStatusCodeValue());
        context.assertIsSatisfied();
    }

  @Test
  public void testDeleteCategory() throws Exception {
    MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
    mockHttpServletRequest.getSession().setAttribute("authenticated", true);

    final String categoryId = "3";

    context.checking(new Expectations(){
      {
        oneOf(categoryDao).deleteCategory(with(any(String.class)));
        will(returnValue(1));
        oneOf(subcategoryDao).deleteSubcategoryByParentId(with(any(String.class)));
        will(returnValue(1));
      }
    });

    final ResponseEntity responseEntity = budgetController.deleteCategory(mockHttpServletRequest, categoryId);
    assertEquals(202, responseEntity.getStatusCodeValue());
    context.assertIsSatisfied();
  }

    @Test
    public void testDeleteSubcategory() throws Exception {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.getSession().setAttribute("authenticated", true);

        final String subcategoryId = "3";

        context.checking(new Expectations(){
            {
                oneOf(subcategoryDao).deleteSubcategory(with(any(String.class)));
                will(returnValue(1));
            }
        });

        final ResponseEntity responseEntity = budgetController.deleteSubcategory(mockHttpServletRequest, subcategoryId);
        assertEquals(202, responseEntity.getStatusCodeValue());
        context.assertIsSatisfied();
    }

  private Budget createBudget() {
    final Budget budget = new Budget();
    budget.setBudgetId(4);
    budget.setBudgetName("TestBudget");
    budget.setBudgetExpenses(1500);
    budget.setStartDate(new Date());
    budget.setEndDate(new Date());
    budget.setBudgetLimit(2000);
    //budget.setCategories(createCategories());
    return budget;
  }

  private List<Category> createCategories(){
    final List<Category> categories = new ArrayList<>();
      final Category category = new Category();
      category.setCategoryId(1);
      category.setCategoryName("Car");
      category.setCategoryExpenses(100);
      category.setCategoryLimit(200);
      categories.add(category);

      final Category secondCategory = new Category();
      secondCategory.setCategoryId(2);
      secondCategory.setCategoryName("Food");
      secondCategory.setCategoryExpenses(160);
      secondCategory.setCategoryLimit(300);
      categories.add(secondCategory);
      return categories;
  }


  private List<Subcategory> createSubcategories(){
      final List<Subcategory> subcategories = new ArrayList<>();
      final Subcategory subcategory = new Subcategory();
      subcategory.setSubcategoryId(1);
      subcategory.setSubcategoryName("Gas");
      subcategory.setSubcategoryExpenses(100);
      subcategory.setSubcategoryLimit(50);
      subcategories.add(subcategory);

      final Subcategory secondSubcategory = new Subcategory();
      secondSubcategory.setSubcategoryId(2);
      secondSubcategory.setSubcategoryName("Misc");
      secondSubcategory.setSubcategoryExpenses(0);
      secondSubcategory.setSubcategoryLimit(100);
      subcategories.add(secondSubcategory);
      return subcategories;
  }
}