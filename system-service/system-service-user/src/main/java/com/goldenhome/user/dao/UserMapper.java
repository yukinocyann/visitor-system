package com.goldenhome.user.dao;

import com.goldenhome.user.pojo.AccessCode;
import com.goldenhome.user.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Repository
public interface UserMapper {
    void update(String loginTime,String phone);

    User selectByPhone(String phone,String localTime, String accessCode);

    void logout(String lastLoginTime, Integer id);

    void insertAccessCode(String accessCode, String reservationExpireTime, Integer id);

    void changeCodeStatus(Integer id);

    void addMember(User user);

    Map countByDaytime(String daytime, String monthTime, String yearTime, String endQueryTime);

    User verify(String phone);

    void reserveInsert(String reservationLoginTime, String reservationExpireTime,String phone);

    List avoid(Integer id, String accessCode);

    List avoidFirst(Integer id);

    void updateReservation(String localTime, String phone);

    void updateUserActualVisitStatus(String localTime);


    AccessCode queryAccessCode(Integer id);

    Map queryStatistics(String phone,String daytime, String monthTime, String yearTime, String endQueryTime);
}
