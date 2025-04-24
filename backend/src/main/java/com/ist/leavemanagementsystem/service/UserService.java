package com.ist.leavemanagementsystem.service;

import com.ist.leavemanagementsystem.dto.UserDto;
import com.ist.leavemanagementsystem.service.UserRoleService;
import com.ist.leavemanagementsystem.repository.UserRepository;
import com.ist.leavemanagementsystem.repository.UserRoleRepository;
import com.ist.leavemanagementsystem.model.Role;
import com.ist.leavemanagementsystem.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import java.util.List;
import com.ist.leavemanagementsystem.model.UserRole;
import com.ist.leavemanagementsystem.repository.RoleRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RoleRepository roleRepository;

    public UserDto getUserDtoFromOAuth(OAuth2User principal) {
        if (principal == null)
            return null;

        String id = principal.getAttribute("sub");
        if (id == null)
            id = principal.getName();

        String name = principal.getAttribute("name");
        String email = principal.getAttribute("email");
        String avatarUrl = principal.getAttribute("picture");
        if (avatarUrl == null)
            avatarUrl = principal.getAttribute("avatar_url");

        // Fetch user from database
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Fetch the single role of the user
        String role = userRoleService.getRolesByUserId(user.getId()).get(0);
        if (role == null) {
            throw new UsernameNotFoundException("Role not found for user: " + user.getId());
        }

        return new UserDto(id, name, email, avatarUrl, role, user.getHashPassword());
    }

    public UserDto getUserDtoFromDatabase(String email) {
        // Fetch user from database
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Fetch the single role of the user
        List<UserRole> userRoles = userRoleRepository.findAll();

        String role = userRoleService.getRolesByUserId(user.getId()).get(0);
        if (role == null) {
            throw new UsernameNotFoundException("Role not found for user: " + user.getId());
        }
        String avatarUrl = user.getProfilePicture();

        return new UserDto(String.valueOf(user.getId()), user.getName(), user.getEmail(), avatarUrl, role,
                user.getHashPassword());
    }

    public UserDto registerUser(User request) {
        // Check if the user already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UsernameNotFoundException("User already exists with email: " + request.getEmail());
        }

        // Save the new user to the database
        User savedUser = userRepository.save(request);

        // Fetch the single role of the user
        String role = userRoleService.getRolesByUserId(savedUser.getId()).get(0);
        if (role == null) {
            throw new UsernameNotFoundException("Role not found for user: " + savedUser.getId());
        }

        return new UserDto(String.valueOf(savedUser.getId()), savedUser.getName(), savedUser.getEmail(),
                savedUser.getProfilePicture(), role, savedUser.getHashPassword());
    }

    private String getRoleByUserId(Long userId) {
        List<UserRole> userRoles = userRoleRepository.findAll();
        Long roleId = null;
        for (UserRole userRole : userRoles) {
            if (userRole.getUserId().equals(userId)) {
                // roleId = userRole.getRoleId();
                break;
            }
        }
        if (roleId == null) {
            throw new UsernameNotFoundException("Role not found for user: " + userId);
        }
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new UsernameNotFoundException("Role not found for ID: " + roleId));
        return role.getName();
    }

}
