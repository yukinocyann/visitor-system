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

/**
 * @Author yukino
 * @Date 2021/7/18 22:21
 * @Description
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    /***
     * 访客登录
     * @param user,accessCode
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody User user, @RequestBody AccessCode accessCode) {
        String username = user.getUsername();

        String password = user.getPassword();

        String accessCodesomeone = accessCode.getAccessCode();

        Integer codeStatus = accessCode.getCodeStatus();

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password) || StringUtils.isEmpty(accessCodesomeone)) {
            return new Result(false, StatusCode.LOGINERROR, "用户名和密码和访问码不能为空");
        }
        User somebody = userService.selectByUsername(username, password, accessCodesomeone, codeStatus);
        if (null == somebody) {
            return new Result(false, StatusCode.LOGINERROR, "用户名或密码错误，访问码错误或失效");
        }
        userService.update(user);

        return new Result(true, StatusCode.OK, "登录成功");
    }

    @PostMapping("/logout")
    public Result logout(@RequestBody User user) {
        user.setStatus(0);

        AccessCode accessCode = new AccessCode();

        String randomCode = RandomStringUtil.getRandomCode(6, 0);

        accessCode.setAccessCode(randomCode);

        accessCode.setCodeStatus(0);

        userService.logout(user.getStatus(), user.getLastLoginTime(),user.getId());

        userService.insertAccessCode(accessCode.getAccessCode(), accessCode.getCodeStatus(), user.getId());

        return new Result(true, StatusCode.OK, "退出成功");

    }
}
