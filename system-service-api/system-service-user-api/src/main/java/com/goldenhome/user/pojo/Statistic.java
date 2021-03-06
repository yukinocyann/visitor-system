package com.goldenhome.user.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author yukino
 * @Date 2021/7/19 14:28
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Statistic {
    //private String date;//

    private String reservationVisitorTotal;//用户

    private String actualVisitorTotal;//用户

    private String standUpVisitorTotal;//用户

}
