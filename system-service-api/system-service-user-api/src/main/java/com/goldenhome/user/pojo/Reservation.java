package com.goldenhome.user.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: yukino
 * @Date: 2021/07/23/11:31
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    private Integer reservationId;//预定记录id

    private String phone;//手机号

    private String reservationLoginTime;//预定开始时间

    private String reservationExpireTime;//预定结束，过期时间

    private String actualVisitTime;//当日实际到访时间

    private String actualVisitStatus;//0爽约，1到访，2预约中

}
