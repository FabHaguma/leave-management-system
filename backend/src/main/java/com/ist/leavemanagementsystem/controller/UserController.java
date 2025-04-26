package com.ist.leavemanagementsystem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.ist.leavemanagementsystem.model.User;
import com.ist.leavemanagementsystem.service.UserRoleService;
import com.ist.leavemanagementsystem.service.UserService;
import com.ist.leavemanagementsystem.dto.UserDto;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    public UserRoleService userRoleService;

    public UserController(UserService userService, UserRoleService userRoleService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody User request) {
        System.out.println(
                "UserController - login - user: " + request.getEmail() + ", password: " + request.getPassword());
        UserDto user = userService.authenticateUser(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody User request) {

        System.out.println(
                "UserController - register - user: " + request.getEmail() + ", password: " + request.getPassword());
        UserDto newUser = userService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }
}
