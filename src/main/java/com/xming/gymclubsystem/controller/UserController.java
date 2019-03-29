package com.xming.gymclubsystem.controller;

import com.xming.gymclubsystem.domain.UmUser;
import com.xming.gymclubsystem.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Xiaoming.
 * Created on 2019/03/29 17:59.
 */
@Slf4j
@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping(path = "signup")
    public String getSignUpForm(){
        return "sign-up";
    }

    @PostMapping(path = "signup")
    public ResponseEntity<UmUser> signUp(
            @RequestParam(value = "username") String username
            ,@RequestParam(value = "password") String password
            ,@RequestParam(value = "email") String email
    ){
        return null;
    }

    @GetMapping(path = "/login")
    public String getLoginForm(){
        return "login";
    }

    @PostMapping(path = "/login")
    @ResponseBody
    public ResponseEntity<UmUser> signIn(@RequestParam(value = "username", required = true) String username
            , @RequestParam(value = "password") String password){
        return null;
    }
}
