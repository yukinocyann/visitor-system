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


//    @Scheduled(cron = "0 55 23 * * ?")
//    public Result statistics() {
//
//        String localTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//
//        Statistic record = userService.check(localTime);
//
//        if (null != record) {
//            return new Result(false, StatusCode.ERROR, "已经统计",
//                    "今日预约访问人数为" + record.getReservationVisitorTotal()
//                            + "今日实际访问人数为" + record.getActualVisitorTotal()
//                            + "今日爽约访问人数为" + record.getStandUpVisitorTotal());
//        }
//
//        String count = userService.count(localTime);
//
//        userService.insertStatistic(localTime, count);
//
//        return new Result(true, StatusCode.OK, "统计成功", "今日预约访问人数为" + count);
//    }

    @Scheduled(cron = "0 0 0/1 * * ? ")
    public Result Novisitor() {

        String localTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        userService.updateUserActualVisitStatus(localTime);

        return new Result(true, StatusCode.OK, "修改成功");
    }
}


