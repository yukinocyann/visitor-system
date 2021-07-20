package com.goldenhome.user.service;

import com.goldenhome.user.pojo.User;


import java.util.Date;

public interface UserService {
    void update(String loginTime,Integer id);

    User selectByUsername(String username, String password, String accessCode);


    void logout(String lastLoginTime, Integer id);

    void insertAccessCode(String accessCode, Integer id);

    void changeCodeStatus(Integer id);

    void addMember(User user);

    Integer count(String localTime);

    User verify(String username);
}
