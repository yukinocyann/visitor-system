package com.goldenhome.user.dao;

import com.goldenhome.user.pojo.User;

import java.util.Date;

public interface UserMapper {
    void update(User user);

    User selectByUsername(String username, String password, String accessCode, Integer codeStatus);

    void logout(Integer status, Date lastLoginTime, Integer id);

    void insertAccessCode(String accessCode, Integer codeStatus, Integer id);
}
