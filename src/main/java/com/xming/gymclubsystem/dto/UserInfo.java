package com.xming.gymclubsystem.dto;

import com.xming.gymclubsystem.domain.primary.Trainer;
import lombok.Data;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Xiaoming.
 * Created on 2019/04/18 17:44.
 */
@Data
public class UserInfo {
    private Integer id;

    private String username;

    private String email;

    private String nickname;

    private Date lastPasswordReset;

    private List<Trainer> trainers = new LinkedList<>();

    public UserInfo() {
    }

    public UserInfo(Integer id, String username, String email, String nickname, Date lastPasswordReset, List<Trainer> trainers) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.nickname = nickname;
        this.lastPasswordReset = lastPasswordReset;
        this.trainers = trainers;
    }
}
