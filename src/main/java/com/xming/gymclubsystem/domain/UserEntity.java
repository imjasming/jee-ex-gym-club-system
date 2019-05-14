package com.xming.gymclubsystem.domain;

import lombok.Data;

@Data
public class UserEntity {
    /**
     * id
     */
    private int id;
    /**
     * 姓名
     */
    private String loginName;
    /**
     * 登录名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     *
     */
    private String email;
    /**
     * 头像url
     */
    private String headimg;
    /**
     * GitHub主页
     */
    private String url;
    /**
     * 注册时间
     */
    private String createTime;

    private String githubid;

    private Integer status;

}
