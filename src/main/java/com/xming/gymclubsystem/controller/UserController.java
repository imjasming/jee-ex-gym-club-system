package com.xming.gymclubsystem.controller;

import com.xming.gymclubsystem.domain.primary.UmUser;
import com.xming.gymclubsystem.dto.UserInfo;
import com.xming.gymclubsystem.dto.UserProfile;
import com.xming.gymclubsystem.dto.UserSignUpRequest;
import com.xming.gymclubsystem.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Xiaoming.
 * Created on 2019/03/29 17:59.
 */
@Slf4j
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @PostMapping(path = "/register")
    public ResponseEntity signUp(
            @RequestBody UserSignUpRequest request
            /*@RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("email") String email*/
    ) {
        UmUser user = userService.register(request);
        if (user != null) {
            return ResponseEntity.ok(null);
        } else {
            return ResponseEntity.badRequest().body("Username or email already exists");
        }
    }

    @PostMapping(path = "/login")
    public ResponseEntity login(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password
    ) {
        String token = userService.login(username, password);

        if (token == null) {
            return ResponseEntity.badRequest().body("Username or password incorrect");
        }

        log.info("user[{}] login, token: {}", username, token);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return ResponseEntity.ok(tokenMap);
    }

    @GetMapping("/user/get-info")
    public ResponseEntity<UserInfo> getUserInfo() {
        final String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        UserInfo userInfo = userService.getUserInfoByName(username);
        return userInfo == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(userInfo);
    }

    @PostMapping("/user/{username}/update-profile")
    public ResponseEntity updateProfile(@PathVariable String username, @RequestBody UserProfile newProfile) {
        newProfile.setUsername(username);
        UserInfo info = userService.updateProfile(newProfile);
        return info != null ? ResponseEntity.ok(info) : ResponseEntity.badRequest().body("Profile update failed");
    }
}
