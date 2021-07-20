package com.goldenhome.user.service.impl;

import com.goldenhome.user.dao.UserMapper;
import com.goldenhome.user.pojo.User;
import com.goldenhome.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;


    @Override
    public void update(String loginTime,Integer id) {
        userMapper.update(loginTime,id);
    }

    @Override
    public User selectByUsername(String username, String password, String accessCode) {
        return userMapper.selectByUsername(username,password,accessCode);
    }

    @Override
    public void logout(String lastLoginTime, Integer id) {
        userMapper.logout(lastLoginTime,id);
    }

    @Override
    public void insertAccessCode(String accessCode,  Integer id) {
        userMapper.insertAccessCode(accessCode, id);
    }

    @Override
    public void changeCodeStatus(Integer id) {
        userMapper.changeCodeStatus(id);
    }

    @Override
    public void addMember(User user) {
        userMapper.addMember(user);
    }

    @Override
    public String count(String localTime) {
        return userMapper.count(localTime);
    }

    @Override
    public User verify(String username) {
        return userMapper.verify(username);
    }

    @Override
    public void insertStatistic(String localTime, String count) {
        userMapper.insertStatistic(localTime,count);
    }

    @Override
    public String check(String localTime) {
        return userMapper.check(localTime);
    }


}
