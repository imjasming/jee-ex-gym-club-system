package com.xming.gymclubsystem.controller;

import com.xming.gymclubsystem.domain.UmUser;
import com.xming.gymclubsystem.dto.UserSignUpParam;
import com.xming.gymclubsystem.repository.UserRepository;
import com.xming.gymclubsystem.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * @author Xiaoming.
 * Created on 2019/03/29 17:59.
 */
@Slf4j
@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping(path = "signup")
    public String getSignUpForm() {
        return "sign-up";
    }

    @PostMapping(path = "signup")
    @ResponseBody
    public ResponseEntity<UmUser> signUp(@RequestBody UserSignUpParam userSignUpParam, BindingResult result) {
        UmUser user = userService.register(userSignUpParam);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping(path = "/login")
    public String getLoginForm() {
        return "login";
    }

    @PostMapping(path = "/login")
    @ResponseBody
    public ResponseEntity<String> login(@RequestParam(value = "username", required = true) String username
            , @RequestParam(value = "password") String password) {
        String token = userService.login(username, password);

        if (token == null) {
            return ResponseEntity.badRequest().body(null);
        }

        return ResponseEntity.ok(token);
    }
}
