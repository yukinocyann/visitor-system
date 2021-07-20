package com.goldenhome.user.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Statistic {
    private String date;//访问码状态(0失效，1正常）

    private String viewers;//用户id
}
