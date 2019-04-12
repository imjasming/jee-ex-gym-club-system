package com.xming.gymclubsystem.controller;

import com.xming.gymclubsystem.domain.UmUser;
import com.xming.gymclubsystem.dto.UserSignUpRequest;
import com.xming.gymclubsystem.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Xiaoming.
 * Created on 2019/03/29 17:59.
 */
@Slf4j
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

/*    @GetMapping(path = "/signup")
    public String getSignUpForm() {
        return "sign-up";
    }*/

    @PostMapping(path = "/signup")
    @ResponseBody
    public ResponseEntity<UmUser> signUp(
            //@RequestBody UserSignUpRequest request
            @RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("email") String email
    ) {
        UmUser user = userService.register(new UserSignUpRequest(username, password, email));
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /*@GetMapping(path = "/login")
    public String getLoginForm() {
        return "login";
    }*/

    @PostMapping(path = "/login")
    @ResponseBody
    public ResponseEntity<Map> login(
            @RequestParam(value = "username") String username
            , @RequestParam(value = "password") String password) {
        String token = userService.login(username, password);

        if (token == null) {
            return ResponseEntity.badRequest().body(null);
        }

        log.info("user[{}] login, token: {}", username, token);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return ResponseEntity.ok(tokenMap);
    }

    @RequestMapping(path = {"/", ""})
    public String index() {
        return "index";
    }
}
