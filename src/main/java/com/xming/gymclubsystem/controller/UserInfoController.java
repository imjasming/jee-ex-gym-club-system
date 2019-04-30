package com.xming.gymclubsystem.controller;

import com.xming.gymclubsystem.domain.primary.UmUser;
import com.xming.gymclubsystem.domain.secondary.UserInfo;
import com.xming.gymclubsystem.dto.UserProfile;
import com.xming.gymclubsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Xiaoming.
 * Created on 2019/04/29 18:18.
 */
@RestController
@RequestMapping("/user")
public class UserInfoController {
    @Autowired
    private UserService userService;

    @GetMapping("/get-info")
    public ResponseEntity<UserInfo> getUserInfo(@RequestAttribute("username") String username) {
        UserInfo userInfo = userService.getUserInfoByName(username);
        return userInfo == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(userInfo);
    }

    @PostMapping("/{username}/update-profile")
    public ResponseEntity updateProfile(@PathVariable String username, @RequestBody UserProfile newProfile) {
        newProfile.setUsername(username);
        UserInfo info = userService.updateProfile(newProfile, username);
        return info != null ? ResponseEntity.ok(info) : ResponseEntity.badRequest().body("Profile update failed");
    }

    @PostMapping("/reset-password")
    public ResponseEntity resetPassword(@RequestParam("oldPassword") String oldPassword,
                                        @RequestParam("newPassword") String newPassword,
                                        @RequestAttribute("username") String username) {
        UmUser user = userService.changePassword(username, oldPassword, newPassword);
        return ResponseEntity.ok(user);
    }
}
