package com.goldenhome.user.controller;

import com.goldenhome.user.pojo.AccessCode;
import com.goldenhome.user.pojo.Reservation;
import com.goldenhome.user.pojo.User;
import com.goldenhome.user.service.UserService;
import entity.RandomStringUtil;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @Author yukino
 * @Date 2021/7/19 10:21
 * @Description
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * @Description: 预约
     * @Param: [user]
     * @return: entity.Result
     * @Author: yukino
     * @Date: 2021/7/21
     */
    @Transactional
    @PostMapping("/reserve")
    public Result reserve(@RequestBody Reservation reservation) {

        String phone = reservation.getPhone();

        String reservationLoginTime = reservation.getReservationLoginTime();

        String reservationExpireTime = reservation.getReservationExpireTime();

        User somebody = userService.verify(phone);

        if (null != somebody) {

            userService.reserveInsert(reservationLoginTime, reservationExpireTime, phone);

            String randomCode = RandomStringUtil.getRandomCode(6, 0);

            userService.insertAccessCode(randomCode, reservationExpireTime, somebody.getId());

            return new Result(true, StatusCode.OK, "预约成功", "访问码为" + randomCode);
        }

        return new Result(false, StatusCode.ERROR, "预约失败,请先用户注册");


    }


    /**
     * @Description: 访客注册
     * @Param: [user]
     * @return: entity.Result
     * @Author: yukino
     * @Date: 2021/7/22
     */
    @Transactional
    @PostMapping("/register")
    public Result register(@RequestBody User user) {

        User somebody = userService.verify(user.getPhone());

        if (null != somebody) {
            return new Result(false, StatusCode.LOGINERROR, "注册失败,已经注册");
        }

        String password = user.getPassword();

        if (StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(password) || StringUtils.isEmpty(user.getPhone())) {
            return new Result(false, StatusCode.LOGINERROR, "用户名和密码和手机号不能为空");
        }

        String encodePassword = passwordEncoder.encode(password);

        user.setPassword(encodePassword);

        try {
            userService.addMember(user);
        } catch (Exception e) {
            return new Result(false, StatusCode.LOGINERROR, "注册失败");
        }

        return new Result(true, StatusCode.OK, "注册成功");
    }

    /**
     * @Description: 申请访客码
     * @Param: [user]
     * @return: entity.Result
     * @Author: yukino
     * @Date: 2021/7/26
     */
    @Transactional
    @PostMapping("/apply")
    public Result apply(@RequestBody User user) {

        String phone = user.getPhone();

        User somebody = userService.verify(phone);

        if (null != somebody) {

            AccessCode accessCode = userService.queryAccessCode(somebody.getId());

            return new Result(true, StatusCode.OK, "注册成功，访客码为" + accessCode.getAccessCode());
        }

        return new Result(false, StatusCode.ERROR, "申请失败,请先注册");
    }

    /**
     * @Description: 访客登录
     * @Param: [user, accessCode]
     * @return: entity.Result
     * @Author: yukino
     * @Date: 2021/7/21
     */
    @Transactional
    @PostMapping("/login")
    public Result login(@RequestBody User user, @RequestParam String accessCode) throws Exception {

        String phone = user.getPhone();

        String password = user.getPassword();

        String localTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password) || StringUtils.isEmpty(accessCode)) {
            return new Result(false, StatusCode.LOGINERROR, "手机号和密码和访问码不能为空");
        }


        User somebody = userService.selectByPhone(phone, localTime, accessCode);

        if (null == somebody) {
            return new Result(false, StatusCode.LOGINERROR, "用户不存在或访问码错误或失效");
        }

        if (somebody.getStatus() == 1) {
            return new Result(false, StatusCode.LOGINERROR, "已经登录，请勿重复登录");
        }

        if (!passwordEncoder.matches(password, somebody.getPassword())) {
            return new Result(false, StatusCode.LOGINERROR, "密码错误");
        }

        userService.update(localTime, somebody.getPhone());

        userService.updateReservation(localTime, phone);

        return new Result(true, StatusCode.OK, "登录成功");
    }

    /***
     * 访客退出
     * @param user
     * @return
     */
    @PostMapping("/logout")
    public Result logout(@RequestBody User user, @RequestParam String accessCode) throws Exception {

        List ownerAccessCodeList = userService.avoidFirst(user.getId());

        if (!ownerAccessCodeList.contains(accessCode)) {
            return new Result(false, StatusCode.ERROR, "此访问码无效,请使用本人访问码");
        }

        List invalidAccessCodeList = userService.avoid(user.getId(), accessCode);

        if (null != invalidAccessCodeList && invalidAccessCodeList.size() > 0) {
            return new Result(false, StatusCode.ERROR, "已经退出,请不要重复退出");
        }

        String localTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        userService.logout(localTime, user.getId());

        userService.changeCodeStatus(user.getId());

        return new Result(true, StatusCode.OK, "退出成功");
    }

    /**
     * @Description: 每日统计
     * @Param: []
     * @return: entity.Result
     * @Author: yukino
     * @Date: 2021/7/21
     */
    @Transactional
    @PostMapping("/statistics")
    public Result statistics(@RequestParam String queryTime, String endQueryTime) throws Exception {

        HashMap<String, Long> dayStandUpVisitorTotalMap = new HashMap<String, Long>();

        String daytime = null;
        String monthTime = null;
        String yearTime = null;
        if (null != queryTime &&  null != endQueryTime ) {
            daytime = queryTime;
            dayStandUpVisitorTotalMap = (HashMap<String, Long>) userService.countByDaytime(daytime, monthTime, yearTime, endQueryTime);
        } else if (queryTime.length() == 10) {
            daytime = queryTime;
            dayStandUpVisitorTotalMap = (HashMap<String, Long>) userService.countByDaytime(daytime, monthTime, yearTime, endQueryTime);
        } else if (queryTime.length() == 7) {
            monthTime = queryTime;
            dayStandUpVisitorTotalMap = (HashMap<String, Long>) userService.countByDaytime(daytime, monthTime, yearTime, endQueryTime);
        } else if (queryTime.length() == 4) {
            yearTime = queryTime;
            dayStandUpVisitorTotalMap = (HashMap<String, Long>) userService.countByDaytime(daytime, monthTime, yearTime, endQueryTime);
        }

        Long standUpVisitorTotal = null;

        Long actualVisitorTotal = null;

        Long reservationVisitorTotal = null;

        for (String key : dayStandUpVisitorTotalMap.keySet()) {
            if ("standUpVisitorTotal".equals(key)) {
                standUpVisitorTotal = dayStandUpVisitorTotalMap.get(key);
            } else if ("actualVisitorTotal".equals(key)) {
                actualVisitorTotal = dayStandUpVisitorTotalMap.get(key);
            } else if ("reservationVisitorTotal".equals(key)) {
                reservationVisitorTotal = dayStandUpVisitorTotalMap.get(key);
            }
        }

        return new Result(true, StatusCode.OK, "统计成功", "爽约访问人数为" + standUpVisitorTotal
                + "实际访问人数为" + actualVisitorTotal
                + "总共访问人数为" + reservationVisitorTotal);
    }

    @Transactional
    @PostMapping("/somebodystatistics")
    public Result somebodystatistics(@RequestBody User user,@RequestParam String queryTime, String endQueryTime){

        String phone = user.getPhone();

        HashMap<String, Long> someBodyMap = new HashMap<String, Long>();

        //someBodyMap = (HashMap<String, Long>) userService.queryStatistics(phone, queryTime, endQueryTime);

        String daytime = null;
        String monthTime = null;
        String yearTime = null;
        if (null != queryTime &&  null != endQueryTime ) {
            daytime = queryTime;
            someBodyMap = (HashMap<String, Long>) userService.queryStatistics(phone,daytime, monthTime, yearTime, endQueryTime);
        } else if (queryTime.length() == 10) {
            daytime = queryTime;
            someBodyMap = (HashMap<String, Long>) userService.queryStatistics(phone,daytime, monthTime, yearTime, endQueryTime);
        } else if (queryTime.length() == 7) {
            monthTime = queryTime;
            someBodyMap = (HashMap<String, Long>) userService.queryStatistics(phone,daytime, monthTime, yearTime, endQueryTime);
        } else if (queryTime.length() == 4) {
            yearTime = queryTime;
            someBodyMap = (HashMap<String, Long>) userService.queryStatistics(phone,daytime, monthTime, yearTime, endQueryTime);
        }

        Long standUpSomebodyTotal = null;

        Long actualSomebodyTotal = null;

        Long reservationSomebodyTotal = null;

        for (String key : someBodyMap.keySet()) {
            if ("standUpSomebodyTotal".equals(key)) {
                standUpSomebodyTotal = someBodyMap.get(key);
            } else if ("actualSomebodyTotal".equals(key)) {
                actualSomebodyTotal = someBodyMap.get(key);
            } else if ("reservationSomebodyTotal".equals(key)) {
                reservationSomebodyTotal = someBodyMap.get(key);
            }
        }
        return new Result(true, StatusCode.OK, "统计成功", "爽约访问次数为"+ standUpSomebodyTotal
                + "实际访问次数为"+ actualSomebodyTotal
                + "总共访问次数为"+ reservationSomebodyTotal );
    }


}



