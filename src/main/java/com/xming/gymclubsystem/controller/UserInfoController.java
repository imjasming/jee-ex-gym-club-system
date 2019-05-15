package com.xming.gymclubsystem.controller;

import com.xming.gymclubsystem.common.annotation.RateLimitAspect;
import com.xming.gymclubsystem.domain.primary.UmUser;
import com.xming.gymclubsystem.domain.secondary.UserInfo;
import com.xming.gymclubsystem.dto.UserProfile;
import com.xming.gymclubsystem.dto.hateoas.UserInfoResource;
import com.xming.gymclubsystem.dto.hateoas.hatoasResourceAssembler.UserInfoResourceAssembler;
import com.xming.gymclubsystem.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author Xiaoming.
 * Created on 2019/04/29 18:18.
 */
@Api(tags = "UserInfoController")
@RestController
@RequestMapping("/user")
public class UserInfoController {
    @Autowired
    private UserService userService;

    @RateLimitAspect(permitsPerSecond=10)
    @ApiOperation("get user's info")
    @GetMapping("/{username}/info")
    public ResponseEntity<UserInfo> getUserInfo(@PathVariable("username") String username) {
        UserInfo userInfo = userService.getUserInfoByName(username);
        UserInfoResource userInfoResource = null;
        if (userInfo!=null){
            //hateoasList
            userInfoResource = new UserInfoResourceAssembler().toResource(userInfo);
            userInfoResource.add(linkTo(methodOn(UserInfoController.class).getUserInfo(username)).withSelfRel());
        }

        //return userInfo == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(userInfo);
        return userInfo == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(userInfoResource.getUserInfo());
    }

    @RateLimitAspect(permitsPerSecond=10)
    @ApiOperation("update user's profile")
    @PutMapping("/{username}/profile")
    public ResponseEntity updateProfile(@PathVariable String username, @RequestBody UserProfile newProfile) {
        newProfile.setUsername(username);
        UserInfo info = userService.updateProfile(newProfile, username);
        UserInfoResource userInfoResource = null;
        if (info!=null){
            //hateoasList
            userInfoResource = new UserInfoResourceAssembler().toResource(info);
            userInfoResource.add(linkTo(methodOn(UserInfoController.class).updateProfile(username,newProfile)).withSelfRel());
        }
        //return info != null ? ResponseEntity.ok(info) : ResponseEntity.badRequest().body("Profile update failed");
        return info != null ? ResponseEntity.ok(userInfoResource.getUserInfo()) : ResponseEntity.badRequest().body("Profile update failed");
    }

    @RateLimitAspect(permitsPerSecond=10)
    @ApiOperation("update user's password")
    @PutMapping("/{username}/password")
    public ResponseEntity resetPassword(@RequestParam("oldPassword") String oldPassword,
                                        @RequestParam("newPassword") String newPassword,
                                        @PathVariable("username") String username) {
        UmUser user = userService.changePassword(username, oldPassword, newPassword);
        return ResponseEntity.ok(user);
    }
}
