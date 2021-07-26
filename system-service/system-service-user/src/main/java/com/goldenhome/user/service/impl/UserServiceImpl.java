package com.goldenhome.user.service.impl;

import com.goldenhome.user.dao.UserMapper;
import com.goldenhome.user.pojo.AccessCode;
import com.goldenhome.user.pojo.User;
import com.goldenhome.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public void update(String loginTime,String phone) {
        userMapper.update(loginTime,phone);
    }

    @Override
    public User selectByPhone(String phone, String localTime,String accessCode) {
        return userMapper.selectByPhone(phone,localTime,accessCode);
    }

    @Override
    public void logout(String lastLoginTime, Integer id) {
        userMapper.logout(lastLoginTime,id);
    }

    @Override
    public void insertAccessCode(String accessCode,String reservationExpireTime, Integer id) {
        userMapper.insertAccessCode(accessCode, reservationExpireTime,id);
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
    public Map countByDaytime(String daytime, String monthTime, String yearTime, String endQueryTime) {
        return userMapper.countByDaytime(daytime, monthTime, yearTime, endQueryTime);
    }

    @Override
    public User verify(String phone) {
        return userMapper.verify(phone);
    }

    @Override
    public void reserveInsert(String reservationLoginTime, String reservationExpireTime,String phone) {
        userMapper.reserveInsert(reservationLoginTime,reservationExpireTime,phone);
    }

    @Override
    public List avoid(Integer id, String accessCode) {
        return userMapper.avoid(id, accessCode);
    }

    @Override
    public List avoidFirst(Integer id) {
        return userMapper.avoidFirst(id);
    }

    @Override
    public void updateReservation(String localTime, String phone) {
        userMapper.updateReservation(localTime,phone);
    }

    @Override
    public void updateUserActualVisitStatus(String queryTime) {
        userMapper.updateUserActualVisitStatus(queryTime);
    }

    @Override
    public AccessCode queryAccessCode(Integer id) {
        return userMapper.queryAccessCode(id);
    }

    @Override
    public Map queryStatistics(String phone,String daytime, String monthTime, String yearTime, String endQueryTime) {
        return userMapper.queryStatistics(phone, daytime, monthTime, yearTime, endQueryTime);
    }


}
