package com.xming.gymclubsystem.service.impl;

import com.xming.gymclubsystem.domain.primary.Role;
import com.xming.gymclubsystem.domain.primary.UmUser;
import com.xming.gymclubsystem.domain.secondary.UserInfo;
import com.xming.gymclubsystem.dto.UserProfile;
import com.xming.gymclubsystem.dto.UserSignUpRequest;
import com.xming.gymclubsystem.repository.primary.RoleRepository;
import com.xming.gymclubsystem.repository.primary.UserRepository;
import com.xming.gymclubsystem.repository.secondary.UserInfoRepository;
import com.xming.gymclubsystem.service.JwtUserDetailsService;
import com.xming.gymclubsystem.service.UserService;
import com.xming.gymclubsystem.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static com.xming.gymclubsystem.domain.primary.Role.RoleName.ROLE_ADMIN;
import static com.xming.gymclubsystem.domain.primary.Role.RoleName.ROLE_USER;

/**
 * @author Xiaoming.
 * Created on 2019/03/29 20:46.
 */
@Slf4j
@Service("userService")
//@CacheConfig(cacheNames = "com.xm.service.userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private void formalAddUserRole(UmUser umUser, Role.RoleName rname) {
        if (roleRepository.existsById(1)) {
            umUser.getRoles().add(roleRepository.findByName(rname));
            userRepository.save(umUser);
        } else {
            Role role1 = new Role(ROLE_USER);
            Role role2 = new Role(ROLE_ADMIN);
            roleRepository.save(role1);
            roleRepository.save(role2);

            umUser.getRoles().add(roleRepository.findByName(rname));
            userRepository.save(umUser);
        }
    }

    @Override
    public UmUser register(UserSignUpRequest signUpParam) {
        UmUser newUser = new UmUser();
        if (userRepository.findByUsername(signUpParam.getUsername()) != null || userRepository.findByEmail(signUpParam.getEmail()) != null) {
            log.warn("username or email exited: {} {}", signUpParam.getUsername(), signUpParam.getEmail());
            return null;
        }

        BeanUtils.copyProperties(signUpParam, newUser);
        final String rawPassword = passwordEncoder.encode(signUpParam.getPassword());
        newUser.setPassword(rawPassword);
        newUser.setLastPasswordReset(new Date());
//        newUser.setRoles(Collections.singletonList(newRole));
        newUser.setEnable(true);
//        roleRepository.save(newRole);
//        userRepository.save(newUser);

        formalAddUserRole(newUser, ROLE_USER);
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
            log.warn("user[{}] login failed: {}", username, e.getMessage());
            e.printStackTrace();
        }

        return token;
    }

    private UserInfo getInfoByUser(UmUser user) {
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(user, userInfo);
        return userInfo;
    }

    @Override
    @Cacheable(value = "umuser", key = "#username")
    public UmUser getUserByName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    //@Cacheable(value = "userInfo", key = "#username")
    public UserInfo getUserInfoByName(String username) {
        UserInfo userInfo = userInfoRepository.findByUsername(username);
        if (userInfo == null) {
            userInfo = getInfoByUser(userRepository.findByUsername(username));
            userInfoRepository.save(userInfo);
        }
        return userInfo;
    }

    @Transactional
    @Override
    //@CachePut(key = "#username")
    public UserInfo updateProfile(UserProfile newProfile, String username) {
        final String email = newProfile.getEmail();
        if (userRepository.existsByEmail(email)) {
            return null;
        }
        userRepository.updateUmUserEmail(username, email);
        userInfoRepository.deleteById(username);

        final UserInfo userInfo = getInfoByUser(userRepository.findByUsername(username));
        userInfoRepository.save(userInfo);

        return userInfo;
    }

    @Transactional(value = "transactionManagerPrimary")
    @Override
    public void changePassword(String uname, String password) {
        userRepository.updateUmUserPassword(uname, password);
    }

}
