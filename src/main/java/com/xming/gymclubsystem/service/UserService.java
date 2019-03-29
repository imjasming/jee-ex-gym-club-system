package com.xming.gymclubsystem.service;

import com.xming.gymclubsystem.domain.UmUser;

/**
 * @author Xiaoming.
 * Created on 2019/03/29 18:21.
 */
public interface UserService {
    UmUser register(String username, String password, String email);

    UmUser login(String username, String password);

    UmUser getUserByName(String username);
}
