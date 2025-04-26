package com.ist.leavemanagementsystem.service;

import com.ist.leavemanagementsystem.dto.UserDto;
import com.ist.leavemanagementsystem.repository.UserRepository;
import com.ist.leavemanagementsystem.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleService userRoleService;

    public UserDto authenticateUser(String email, String rawPassword) {
        // Fetch user from the database
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Check if the user is a test user, we don't need to verify the password
        if (!user.getEmail().contains("test")) {
            // Verify the password
            if (!BCrypt.checkpw(rawPassword, user.getPassword())) {
                throw new UsernameNotFoundException("Invalid credentials-BE");
            }
        }

        // Fetch the single role of the user
        String role = userRoleService.getRoleByUserId(user.getId());
        if (role == null || role.isEmpty()) {
            throw new UsernameNotFoundException("Role not found for user: " + user.getEmail());
        }

        System.out.println(
                "UserService - authenticateUser - user: " + user.getEmail() + ", rawPassword: " + rawPassword
                        + ", role: "
                        + role + ", password: " + user.getPassword());

        // Return the authenticated user's details
        return new UserDto(user.getName(), user.getEmail(), user.getProfilePicture(), role);
    }

    public UserDto registerUser(User request) {
        // Check if the user already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UsernameNotFoundException("User already exists with email: " + request.getEmail());
        }

        // Hash the password before saving
        String hashedPassword = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());
        request.setPassword(hashedPassword);

        System.out.println(
                "UserService - registerUser - user: " + request.getEmail() + ", password: " + request.getPassword());

        String role = null;

        // Assign "STAFF" to @staff.com emails and "MANAGER" to @manager.com" and
        // "ADMIN" to @admin.com emails
        if (request.getEmail().endsWith("@staff.com")) {
            role = "STAFF";
            request.setRole(role); // Set the role for the user
        } else if (request.getEmail().endsWith("@manager.com")) {
            role = "MANAGER";
            request.setRole(role); // Set the role for the user
        } else if (request.getEmail().endsWith("@admin.com")) {
            role = "ADMIN";
            request.setRole(role); // Set the role for the user
        } else if (!request.getEmail().endsWith("@ist.com")) {
            throw new UsernameNotFoundException("Invalid email domain: " + request.getEmail());
        }

        // Save the new user to the database // save user first to get the ID while
        // assigning the role
        userRepository.save(request);

        // Assign the role to the user in the user_role table
        userRoleService.assignRoleToUser(request.getEmail(), role);

        return new UserDto(request.getName(), request.getEmail(), request.getProfilePicture(), role);
    }

}
