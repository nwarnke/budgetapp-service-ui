package com.dao;

import com.dto.UserDto;

import java.util.List;

public interface IUserDao {

    List<UserDto> getLoginInfo(String name, String password);
}
