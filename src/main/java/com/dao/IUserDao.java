package com.dao;

import com.dto.User;

import java.util.List;

public interface IUserDao {

    List<User> getUserPassword(String name);
    boolean updateUserInfo(String username, String newPassword, String oldPassword);
    boolean saveUserInfo(String firstname, String lastname, String username, int userid);
}
