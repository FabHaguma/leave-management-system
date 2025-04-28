package com.ist.leavemanagementsystem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.ist.leavemanagementsystem.model.User;
import com.ist.leavemanagementsystem.service.UserRoleService;
import com.ist.leavemanagementsystem.service.UserService;
import com.ist.leavemanagementsystem.dto.UserDto;
import java.util.List;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    public UserRoleService userRoleService;

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

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        UserDto user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }
}
