package com.goldenhome.user.task;


import com.goldenhome.user.service.UserService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class QueryViewers {

    @Autowired
    private UserService userService;


    @Scheduled(cron = "0 0 0/1 * * ? ")
    public Result Novisitor() {

        userService.updateUserActualVisitStatus();

        return new Result(true, StatusCode.OK, "修改成功");
    }
}


