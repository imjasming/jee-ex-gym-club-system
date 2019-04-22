package com.xming.gymclubsystem.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author Xiaoming.
 * Created on 2019/04/21 16:48.
 */
@Data
public class UserProfile {
    private String username;
    @NotEmpty(message = "username can't be empty")
    private String email;

    public UserProfile() {
    }
}
