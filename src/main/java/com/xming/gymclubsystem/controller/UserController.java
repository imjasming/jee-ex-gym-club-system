package com.xming.gymclubsystem.controller;

import com.xming.gymclubsystem.domain.UmUser;
import com.xming.gymclubsystem.dto.RestResponse;
import com.xming.gymclubsystem.dto.UserSignUpRequest;
import com.xming.gymclubsystem.service.DataService;
import com.xming.gymclubsystem.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
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
public class UserController {
    @Autowired
    private UserService userService;


    //数据库操作service
    @Autowired
    private DataService dataService;


    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @PostMapping(path = "/register")
    public RestResponse signUp(
            //@RequestBody UserSignUpRequest request
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("email") String email
    ) {
        UmUser user = userService.register(new UserSignUpRequest(username, password, email));
        if (user != null) {
            return RestResponse.ok(user);
        } else {
            return RestResponse.badRequest("Username or password already exists");
        }
    }

    @PostMapping(path = "/login")
    public RestResponse login(
            @RequestParam(value = "username") String username
            , @RequestParam(value = "password") String password) {
        String token = userService.login(username, password);

        if (token == null) {
            return RestResponse.badRequest("Username or password incorrect");
        }

        log.info("user[{}] login, token: {}", username, token);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return RestResponse.ok(tokenMap);
    }
}
