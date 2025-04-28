package com.ist.leavemanagementsystem.service;

import com.ist.leavemanagementsystem.dto.UserDto;
import com.ist.leavemanagementsystem.repository.UserRepository;
import com.ist.leavemanagementsystem.model.User;
import com.ist.leavemanagementsystem.util.MapperUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import com.ist.leavemanagementsystem.service.LookupService;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private LookupService lookupService;
    @Autowired
    private MapperUtil mapperUtil;

    public UserDto authenticateUser(String email, String rawPassword) {
        // Fetch user from the database
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Check if the user is a test user, we don't need to verify the password
        if (!user.getEmail().contains("test")) {
            // Verify the password to allow seeded users to login without password
            if (!user.getPassword().contains("password")) {
                // If the password is not "password", check the raw password against the
                // hashed password
                if (!BCrypt.checkpw(rawPassword, user.getPassword())) {
                    throw new UsernameNotFoundException("Invalid credentials-BE");
                }
            }

        }

        // Return the authenticated user's details
        return mapperUtil.mapToUserDto(user);
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
        } else if (request.getEmail().endsWith("@manager.com")) {
            role = "MANAGER";
        } else if (request.getEmail().endsWith("@admin.com")) {
            role = "ADMIN";
        } else if (!request.getEmail().endsWith("@ist.com")) {
            throw new UsernameNotFoundException("Invalid email domain: " + request.getEmail());
        }

        // Save the new user to the database // save user first to get the ID while
        // assigning the role
        User user = userRepository.save(request);
        lookupService.addUserRoleToLookup(user.getId(), role);

        // Assign the role to the user in the user_role table
        userRoleService.assignRoleToUser(request.getEmail(), role);

        return mapperUtil.mapToUserDto(user);
    }

    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + id));
        return mapperUtil.mapToUserDto(user);
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = users.stream()
                .map(user -> mapperUtil.mapToUserDto(user))
                .toList();
        return userDtos;
    }
}
