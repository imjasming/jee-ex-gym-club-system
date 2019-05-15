package com.xming.gymclubsystem.controller;

import com.xming.gymclubsystem.common.annotation.RateLimitAspect;
import com.xming.gymclubsystem.domain.primary.UmUser;
import com.xming.gymclubsystem.dto.UserSignUpRequest;
import com.xming.gymclubsystem.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author Xiaoming.
 * Created on 2019/05/10 17:49.
 */
public class UserSignUpController {
    @Autowired
    private UserService userService;

    @RateLimitAspect(permitsPerSecond=10)
    @ApiOperation("sign up")
    @PostMapping(path = "/register")
    public ResponseEntity signUp(
            @ApiParam(required = true, name = "user sign up params", value = "{username, email, password}")
            @RequestBody UserSignUpRequest request) {
        UmUser user = userService.register(request);
        if (user != null) {
            return ResponseEntity.ok(null);
        } else {
            return ResponseEntity.badRequest().body("Username or email already exists");
        }
        //return user == null ? ResponseEntity.badRequest().body("Username or email already exists") : ResponseEntity.created(null).build();
    }
}
