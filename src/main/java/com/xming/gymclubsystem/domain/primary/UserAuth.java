package com.xming.gymclubsystem.domain.primary;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Xiaoming.
 * Created on 2019/05/05 20:41.
 * desciption: 用户授权表，兼容第三方登录，保存用户授权信息，与 User 表为多对一关系
 */
@Data
@Entity
public class UserAuth implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_auth_seq")
    @SequenceGenerator(name = "user_auth_seq", sequenceName = "user_auth_seq", allocationSize = 1)
    private Integer id;
    /**
     * user 表的 id
     */
    private Integer userId;
    /**
     * 认证类型，phone number，email，username，或第三方应用名称，weibo，weixin
     */
    private String identityType;
    /**
     * 认证标识，18801283506，1@1.com，username，或第三方应用标识，微博id，微信
     */
    private String identifier;
    /**
     * 凭据，密码或第三方token（站外也可不保存）
     */
    @JsonIgnore
    private String credential;
}
