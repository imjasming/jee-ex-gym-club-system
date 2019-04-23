package com.xming.gymclubsystem.dto;

import com.xming.gymclubsystem.domain.primary.Trainer;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Xiaoming.
 * Created on 2019/04/18 17:44.
 */
public class UserInfo {
    private Long id;

    private String username;

    private String email;

    private String nickname;

    private List<Trainer> trainers = new LinkedList<>();

    public UserInfo() {
    }

    public UserInfo(Long id, String username, String email, String nickname, List<Trainer> trainers) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.nickname = nickname;
        this.trainers = trainers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public List<Trainer> getTrainers() {
        return trainers;
    }

    public void setTrainers(List<Trainer> trainers) {
        this.trainers = trainers;
    }
}
