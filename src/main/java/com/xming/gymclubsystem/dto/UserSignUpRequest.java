package com.xming.gymclubsystem.dto;

import java.io.Serializable;

/**
 * @author Xiaoming.
 * Created on 2019/04/07 21:24.
 */
public class UserSignUpRequest implements Serializable {
    private static final long serialVersionUID = 8660911965863240049L;

    private String username;
    private String password;
    private String email;

    public UserSignUpRequest(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
