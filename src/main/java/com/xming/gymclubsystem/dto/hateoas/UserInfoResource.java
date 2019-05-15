package com.xming.gymclubsystem.dto.hateoas;

import com.xming.gymclubsystem.domain.primary.UmUser;
import com.xming.gymclubsystem.domain.secondary.UserInfo;
import org.springframework.hateoas.ResourceSupport;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserInfoResource extends ResourceSupport {

    private final UserInfo userInfo;

    public UserInfoResource(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }
}

