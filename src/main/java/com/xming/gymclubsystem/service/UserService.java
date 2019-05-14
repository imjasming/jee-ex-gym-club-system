package com.xming.gymclubsystem.service;

import com.xming.gymclubsystem.domain.primary.UmUser;
import com.xming.gymclubsystem.domain.secondary.UserInfo;
import com.xming.gymclubsystem.dto.UserProfile;
import com.xming.gymclubsystem.dto.UserSignUpRequest;

/**
 * @author Xiaoming.
 * Created on 2019/03/29 18:21.
 */
public interface UserService {
    UmUser createUser(UserSignUpRequest signUpParam);

    UmUser register(UserSignUpRequest signUpParam);

    String login(String username, String password);

    UmUser getUserByName(String username);

    UserInfo getUserInfoByName(String username);

    UserInfo updateProfile(UserProfile newProfile, String username);

    //changePassword
    UmUser changePassword(String username, String oldPassword, String password);
}
