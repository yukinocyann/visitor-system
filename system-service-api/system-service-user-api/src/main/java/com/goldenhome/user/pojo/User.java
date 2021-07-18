package com.goldenhome.user.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author yukino
 * @Date 2021/7/18 22:28
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {


        private Integer id;//用户id


        private String username;//用户名


        private String password;//密码


        private String phone;//注册手机号


        private Date creatTime;//创建时间



        private String name;//真实姓名


        private Integer status;//使用状态（1正常 0离开）


        private Date lastLoginTime;//最后登录时间
}
