package com.goldenhome.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author yukino
 * @Date 2021/7/18 21:31
 * @Description
 */
@SpringBootApplication
@MapperScan(basePackages = "com.goldenhome.user.dao")
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class,args);
    }
}
