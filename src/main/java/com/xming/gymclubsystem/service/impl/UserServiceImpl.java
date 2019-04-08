package com.xming.gymclubsystem.service.impl;

import com.xming.gymclubsystem.domain.Role;
import com.xming.gymclubsystem.domain.UmUser;
import com.xming.gymclubsystem.dto.UserSignUpParam;
import com.xming.gymclubsystem.repository.UserRepository;
import com.xming.gymclubsystem.service.JwtUserDetailsService;
import com.xming.gymclubsystem.service.UserService;
import com.xming.gymclubsystem.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;

/**
 * @author Xiaoming.
 * Created on 2019/03/29 20:46.
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public UmUser register(UserSignUpParam signUpParam) {
        UmUser newUser = new UmUser();
        if (userRepository.findByUsername(signUpParam.getUsername()) != null || userRepository.findByEmail(signUpParam.getEmail()) != null) {
            log.warn("username or email exited: {} {}", signUpParam.getUsername(), signUpParam.getEmail());
            return null;
        }

        BeanUtils.copyProperties(signUpParam, newUser);
        final String rawPassword = passwordEncoder.encode(signUpParam.getPassword());
        newUser.setPassword(rawPassword);
        newUser.setLastPasswordReset(new Date());
        newUser.setRoles(Collections.singletonList(new Role(Role.RoleName.ROLE_USER)));
        newUser.setEnable(true);

        userRepository.save(newUser);
        return newUser;
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        try {
            final Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            token = jwtTokenUtil.generateToken(userDetails);

            //TODO: maybe we can update user's lastlogin or log
            log.info("user[{}] login", username);
        } catch (AuthenticationException e) {
            //log.warn("login failed: {}", e.getMessage());
            e.printStackTrace();
        }

        return token;
    }

    @Override
    public UmUser getUserByName(String username) {
        return null;
    }
}
