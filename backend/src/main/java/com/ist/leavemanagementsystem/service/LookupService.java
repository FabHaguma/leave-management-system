package com.ist.leavemanagementsystem.service;

import com.ist.leavemanagementsystem.model.Role;
import com.ist.leavemanagementsystem.model.User;
import com.ist.leavemanagementsystem.model.UserRole;
import com.ist.leavemanagementsystem.model.LeaveType;
import com.ist.leavemanagementsystem.repository.RoleRepository;
import com.ist.leavemanagementsystem.repository.LeaveTypeRepository;
import com.ist.leavemanagementsystem.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LookupService {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private LeaveTypeRepository leaveTypeRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;

    private Map<Long, String> roleNamesById = new HashMap<>();
    private Map<Long, String> leaveTypeNamesById = new HashMap<>();
    private Map<Long, String> userRoleNameByUserId = new HashMap<>();

    // Preload data at application startup
    @PostConstruct
    public void initialize() {
        List<Role> roles = roleRepository.findAll();
        for (Role role : roles) {
            roleNamesById.put(role.getId(), role.getName());
        }

        List<LeaveType> leaveTypes = leaveTypeRepository.findAll();
        for (LeaveType leaveType : leaveTypes) {
            leaveTypeNamesById.put(leaveType.getId(), leaveType.getName());
        }

        List<UserRole> userRoles = userRoleRepository.findAll();
        for (UserRole userRole : userRoles) {
            userRoleNameByUserId.put(userRole.getUserId(), roleNamesById.get(userRole.getRoleId()));
        }
    }

    // Retrieve role name by ID
    public String getRoleNameById(Long roleId) {
        return roleNamesById.get(roleId);
    }

    // Retrieve leave type name by ID
    public String getLeaveTypeNameById(Long leaveTypeId) {
        return leaveTypeNamesById.get(leaveTypeId);
    }

    // Retrieve role name by user ID
    public String getRoleNameByUserId(Long userId) {
        return userRoleNameByUserId.get(userId);
    }

    public void addRoleToLookup(Long roleId, String roleName) {
        roleNamesById.put(roleId, roleName);

        Role role = new Role();
        role.setId(roleId);
        role.setName(roleName);
        roleRepository.save(role);

    }

    public void addLeaveTypeToLookup(Long leaveTypeId, String LeaveTypeName) {
        leaveTypeNamesById.put(leaveTypeId, LeaveTypeName);

        LeaveType leaveType = new LeaveType(); // Create a new LeaveType object
        leaveType.setId(leaveTypeId); // Set the leave type ID
        leaveType.setName(LeaveTypeName);
        leaveTypeRepository.save(leaveType);
    }

    public void addUserRoleToLookup(Long userId, String roleName) {
        userRoleNameByUserId.put(userId, roleName);

        // Save the user role to the database
        UserRole userRole = new UserRole(); // Create a new UserRole object
        userRole.setUserId(userId); // Set the user ID
        userRole.setRoleId(roleRepository.findByName(roleName).getId()); // Set a default role ID (or handle as needed)
        userRoleRepository.save(userRole); // Save to database
    }
}