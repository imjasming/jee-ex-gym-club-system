package com.xming.gymclubsystem.domain.secondary;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Xiaoming.
 * Created on 2019/04/18 17:44.
 */
@Data
@Entity
public class UserInfo implements Serializable {
    private Integer id;

    @Id
    private String username;

    private String email;

    private String nickname;

    private Date lastPasswordReset;

    private String intro;

    public UserInfo() {
    }
}
