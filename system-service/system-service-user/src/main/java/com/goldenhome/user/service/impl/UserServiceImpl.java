package com.goldenhome.user.service.impl;

import com.goldenhome.user.dao.UserMapper;
import com.goldenhome.user.pojo.User;
import com.goldenhome.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;


    @Override
    public void update(User user) {
        userMapper.update(user);
    }

    @Override
    public User selectByUsername(String username, String password, String accessCode, Integer codeStatus) {
        return userMapper.selectByUsername(username,password,accessCode,codeStatus);
    }

    @Override
    public void logout(Integer status, Date lastLoginTime, Integer id) {
        userMapper.logout(status,lastLoginTime,id);
    }

    @Override
    public void insertAccessCode(String accessCode, Integer codeStatus, Integer id) {
        userMapper.insertAccessCode(accessCode, codeStatus, id);
    }


}
