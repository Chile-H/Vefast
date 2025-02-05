package com.github.chileh.model.users.vo;

import lombok.Data;

@Data
public class LoginVo {

    /**
     * 用户id
     */
    private Integer id;

    /**
     * 用户名
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

}
