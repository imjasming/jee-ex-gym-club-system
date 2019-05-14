package com.xming.gymclubsystem.service;

import com.xming.gymclubsystem.domain.primary.UmUser;
import com.xming.gymclubsystem.dto.GithubAuthServiceResult;

/**
 * @author Xiaoming.
 * Created on 2019/05/15 00:11.
 */
public interface GithubService {
    UmUser getUserByGithubId(String id);

    GithubAuthServiceResult getTokenByUsername(String username);
}
