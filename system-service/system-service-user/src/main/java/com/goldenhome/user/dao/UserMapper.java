package com.goldenhome.user.dao;

import com.goldenhome.user.pojo.User;
import org.springframework.stereotype.Repository;


@Repository
public interface UserMapper {
    void update(String loginTime,Integer id);

    User selectByUsername(String username, String password, String accessCode);

    void logout(String lastLoginTime, Integer id);

    void insertAccessCode(String accessCode,  Integer id);

    void changeCodeStatus(Integer id);

    void addMember(User user);

    Integer count(String localTime);

    User verify(String username);
}
