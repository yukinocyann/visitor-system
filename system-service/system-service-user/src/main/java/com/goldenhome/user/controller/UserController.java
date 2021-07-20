package com.goldenhome.user.controller;

import com.goldenhome.user.pojo.AccessCode;
import com.goldenhome.user.pojo.User;
import com.goldenhome.user.service.UserService;
import entity.RandomStringUtil;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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

    /***
     * 访客注册
     * @param
     * @return
     */
    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        String username = user.getUsername();

        User somebody = userService.verify(username);

        if (null != somebody) {
            return new Result(false, StatusCode.LOGINERROR, "用户名重复");
        }

        String password = user.getPassword();

        String phone = user.getPhone();

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password) || StringUtils.isEmpty(phone)) {
            return new Result(false, StatusCode.LOGINERROR, "用户名和密码和手机号不能为空");
        }

        try {
            userService.addMember(user);
        } catch (Exception e) {
            return new Result(false, StatusCode.LOGINERROR, "注册失败");
        }

        return new Result(true, StatusCode.OK, "注册成功");
    }


    /***
     * 访客登录
     * @param user,accessCode
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody User user, @RequestParam String accessCode) throws Exception {
        String username = user.getUsername();

        String password = user.getPassword();


        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password) || StringUtils.isEmpty(accessCode)) {
            return new Result(false, StatusCode.LOGINERROR, "用户名和密码和访问码不能为空");
        }

        User somebody = userService.selectByUsername(username, password, accessCode);

        if (null == somebody) {
            return new Result(false, StatusCode.LOGINERROR, "用户名或密码错误，访问码错误或失效");
        }

        String localTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        userService.update(localTime, somebody.getId());

        return new Result(true, StatusCode.OK, "登录成功");
    }

    /***
     * 访客退出
     * @param user
     * @return
     */
    @PostMapping("/logout")
    public Result logout(@RequestBody User user) throws Exception {


        String randomCode = RandomStringUtil.getRandomCode(6, 0);

        String localTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        userService.logout(localTime, user.getId());

        userService.changeCodeStatus(user.getId());

        userService.insertAccessCode(randomCode, user.getId());

        return new Result(true, StatusCode.OK, "退出成功");
    }

    /***
     * 访客统计
     * @param user
     * @return
     */
    @PostMapping("/statistics")
    public Result statistics() {

        String localTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Integer count = userService.count(localTime);

        return new Result(true, StatusCode.OK, "统计成功", count);
    }


}
