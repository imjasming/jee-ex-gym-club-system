package com.xming.gymclubsystem.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Xiaoming.
 * Created on 2019/04/07 21:24.
 */
@Data
public class UserSignUpParam {
    @Size(min = 6, max = 20)
    @NotNull
    private String username;
    @Size(min = 6, max = 20)
    private String password;
    @Email
    private String email;

    public UserSignUpParam(@Size(min = 6, max = 20) @NotNull String username, @Size(min = 6, max = 20) String password, @Email String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
