package com.ist.leavemanagementsystem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.ist.leavemanagementsystem.model.User; // Import the User class
import com.ist.leavemanagementsystem.repository.UserRepository;
import com.ist.leavemanagementsystem.service.UserRoleService;
import com.ist.leavemanagementsystem.service.UserService;
import com.ist.leavemanagementsystem.dto.UserDto;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final UserRoleService userRoleService; // Add UserRoleService dependency

    public UserController(UserService userService, UserRepository userRepository, UserRoleService userRoleService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.userRoleService = userRoleService; // Initialize UserRoleService
    }

    // Get current user info (including avatar from Microsoft profile)
    @GetMapping("/me")
    public UserDto getCurrentUser(@AuthenticationPrincipal OAuth2User principal) {
        return userService.getUserDtoFromOAuth(principal);
    }

    public UserDto authenticateUser(String email, String rawPassword) {
        // Fetch user from the database
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Verify the password
        if (!BCrypt.checkpw(rawPassword, user.getHashPassword())) {
            throw new UsernameNotFoundException("Invalid credentials");
        }

        // Fetch the single role of the user
        String role = userRoleService.getRolesByUserId(user.getId()).get(0);
        if (role == null) {
            throw new UsernameNotFoundException("Role not found for user: " + user.getId());
        }

        // Return the authenticated user's details
        return new UserDto(String.valueOf(user.getId()), user.getName(), user.getEmail(), user.getProfilePicture(),
                role,
                user.getHashPassword());
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody User request) {
        UserDto user = authenticateUser(request.getEmail(), request.getHashPassword());
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody User request) {

        UserDto newUser = userService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

}
