package com.xming.gymclubsystem.service.impl;

import com.xming.gymclubsystem.auth.AuthUserDetailsService;
import com.xming.gymclubsystem.auth.jwt.JwtTokenUtil;
import com.xming.gymclubsystem.domain.primary.UmUser;
import com.xming.gymclubsystem.dto.GithubAuthServiceResult;
import com.xming.gymclubsystem.repository.primary.UserRepository;
import com.xming.gymclubsystem.service.GithubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * @author Xiaoming.
 * Created on 2019/05/15 00:12.
 */
@Service("githubService")
public class GithubServiceImpl implements GithubService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private AuthUserDetailsService userDetailsService;

    @Override
    public UmUser getUserByGithubId(String id) {

        return userRepository.findByGithubId(id);
    }

    @Override
    public GithubAuthServiceResult getTokenByUsername(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String token = jwtTokenUtil.generateToken(userDetails);
        return GithubAuthServiceResult.GithubAuthServiceResultBuilder.aGithubAuthServiceResult()
                .withToken(token)
                .withUsername(username)
                .build();
    }
}
