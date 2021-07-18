package com.goldenhome.user.controller;

import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.*;

/**
 * @Author yukino
 * @Date 2021/7/18 22:21
 * @Description
 */
@RestController
@RequestMapping("/User")
@CrossOrigin
public class UserController {
    /***
     * 新增品牌数据
     * @param User
     * @return
     */
    @PostMapping
    public Result add(@RequestBody User user){
        userService.add(user);
        return new Result(true, StatusCode.OK,"添加成功");
    }
}
