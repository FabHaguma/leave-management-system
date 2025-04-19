package com.ist.leavemanagementsystem.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import com.ist.leavemanagementsystem.service.UserService;
import com.ist.leavemanagementsystem.dto.UserDto;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Get current user info (including avatar from Microsoft profile)
    @GetMapping("/me")
    public UserDto getCurrentUser(@AuthenticationPrincipal OAuth2User principal) {
        return userService.getUserDtoFromOAuth(principal);
    }
}
