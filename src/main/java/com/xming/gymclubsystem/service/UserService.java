package com.xming.gymclubsystem.service;

import com.xming.gymclubsystem.domain.UmUser;
import com.xming.gymclubsystem.dto.UserSignUpRequest;

/**
 * @author Xiaoming.
 * Created on 2019/03/29 18:21.
 */
public interface UserService {
    UmUser register(UserSignUpRequest signUpParam);

    String login(String username, String password);

    UmUser getUserByName(String username);
}
