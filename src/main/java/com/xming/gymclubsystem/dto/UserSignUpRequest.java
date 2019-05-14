package com.xming.gymclubsystem.dto;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author Xiaoming.
 * Created on 2019/04/07 21:24.
 */
public class UserSignUpRequest implements Serializable {
    private static final long serialVersionUID = 8660911965863240049L;

    @NotEmpty(message = "username can't be empty")
    private String username;
    @NotEmpty(message = "password can't be empty")
    private String password;
    @NotEmpty(message = "email can't be empty")
    private String email;
    private String githubId;

    public UserSignUpRequest() {
    }

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


    @Override
    public String toString() {
        return "UserSignUpRequest{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public String getGithubId() {
        return githubId;
    }

    public void setGithubId(String githubId) {
        this.githubId = githubId;
    }
}
