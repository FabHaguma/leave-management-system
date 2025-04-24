package com.ist.leavemanagementsystem.service;

import com.ist.leavemanagementsystem.model.Role;
import com.ist.leavemanagementsystem.model.User;
import com.ist.leavemanagementsystem.model.UserRole;
import com.ist.leavemanagementsystem.repository.UserRepository;
import com.ist.leavemanagementsystem.repository.RoleRepository;
import com.ist.leavemanagementsystem.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserRoleService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    public void assignRoleToUser(Long userId, Long roleId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new RuntimeException("Role not found"));

        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);

        userRoleRepository.save(userRole);
    }

    public List<String> getRolesByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return userRoleRepository.findRolesByUserId(user.getId())
                .stream()
                .map(Role::getName) // Assuming Role has a getName() method
                .toList();
    }

    public void removeRoleFromUser(Long userId, Long roleId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new RuntimeException("Role not found"));

        UserRole userRole = userRoleRepository.findByUserAndRole(user, role);

        userRoleRepository.delete(userRole);
    }
}
