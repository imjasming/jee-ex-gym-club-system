package com.xming.gymclubsystem.util;

import com.xming.gymclubsystem.domain.primary.UmUser;
import com.xming.gymclubsystem.domain.secondary.UserInfo;
import com.xming.gymclubsystem.repository.primary.UserRepository;
import org.springframework.beans.BeanUtils;

/**
 * @author Xiaoming.
 * Created on 2019/04/21 17:06.
 */
public class UserUtil {
    private UserRepository userRepository;

    public UserUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserInfo getUserInfoByUser(UmUser user) {
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(user, userInfo);
        return userInfo;
    }


}
