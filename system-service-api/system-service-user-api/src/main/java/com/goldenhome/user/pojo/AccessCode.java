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
public class AccessCode {

    private String accessCode;//访问码

    private Integer codeStatus;//访问码状态(0失效，1正常）

    private Integer userId;//用户id

}
