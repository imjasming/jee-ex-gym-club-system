package com.xming.gymclubsystem.controller;

import com.xming.gymclubsystem.domain.primary.UmUser;
import com.xming.gymclubsystem.dto.UserSignUpRequest;
import com.xming.gymclubsystem.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Xiaoming.
 * Created on 2019/03/29 17:59.
 */
@Slf4j
@RestController
public class UserSignController {
    @Autowired
    private UserService userService;

    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @PostMapping(path = "/register")
    public ResponseEntity signUp(@RequestBody UserSignUpRequest request) {
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
}
