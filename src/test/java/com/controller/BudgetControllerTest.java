package com.controller;

import com.dao.IBudgetDao;
import com.dao.ICategoryDao;
import com.dao.impl.BudgetDao;
import com.dao.impl.CategoryDao;
import com.dto.UserDto;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

public class BudgetControllerTest {

    private Mockery context;
    private BudgetController budgetController;

    @Before
    public void setUp() throws Exception {
        context = new Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        IBudgetDao budgetDao = context.mock(BudgetDao.class);
        ICategoryDao categoryDao = context.mock(CategoryDao.class);
        budgetController = new BudgetController(budgetDao, categoryDao);
    }

    @Ignore
    @Test
    public void getBudget() throws Exception {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.getSession().setAttribute("authenticated", true);
        UserDto userDto = new UserDto();
        userDto.setPassword("password");
        userDto.setUserId("test");
        userDto.setFirstName("john");
        userDto.setLastName("smith");
        userDto.setUserName("testing");
        mockHttpServletRequest.getSession().setAttribute("userInfo", userDto);
        String budgetId = "12345";
        budgetController.getBudget(mockHttpServletRequest, budgetId);
    }

    @Test
    public void newBudget() throws Exception {

    }

}