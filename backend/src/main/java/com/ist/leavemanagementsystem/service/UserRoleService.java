package com.ist.leavemanagementsystem.service;

import com.ist.leavemanagementsystem.model.Role;
import com.ist.leavemanagementsystem.model.User;
import com.ist.leavemanagementsystem.model.UserRole;
import com.ist.leavemanagementsystem.repository.UserRepository;
import com.ist.leavemanagementsystem.repository.RoleRepository;
import com.ist.leavemanagementsystem.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    public void assignRoleToUser(Long userId, Long roleId) {

        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);

        userRoleRepository.save(userRole);
    }

    public void assignRoleToUser(String userEmail, String roleName) {
        Role role = roleRepository.findByName(roleName);
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("User not found"));

        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        userRole.setRoleId(role.getId());
        // userRole.setRoleName(role.getName());

        userRoleRepository.save(userRole);
    }

    // public String getRoleByUserId(Long userId) {
    // List<UserRole> userRoleList = userRoleRepository.findAll();
    // for (UserRole ur : userRoleList) {
    // if (ur.getUserId() == userId) {
    // return ur.getRoleName();
    // }
    // }
    // return "";
    // }
}
