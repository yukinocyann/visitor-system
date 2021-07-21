package com.goldenhome.user.dao;

import com.goldenhome.user.pojo.User;
import org.springframework.stereotype.Repository;


@Repository
public interface UserMapper {
    void update(String loginTime,Integer id);

    User selectByUsername(String username, String accessCode);

    void logout(String lastLoginTime, Integer id);

    void insertAccessCode(String accessCode,  Integer id);

    void changeCodeStatus(Integer id);

    void addMember(User user);

    String count(String localTime);

    User verify(String username);

    void insertStatistic(String localTime, String count);

    String check(String localTime);
}
