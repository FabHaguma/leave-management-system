package com.ist.leavemanagementsystem.util;

import com.ist.leavemanagementsystem.dto.*;
import com.ist.leavemanagementsystem.model.*;
import com.ist.leavemanagementsystem.service.LookupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MapperUtil {

    @Autowired
    private LookupService lookupService;

    // LeaveBalance Mapping
    public LeaveBalanceDto mapToLeaveBalanceDto(LeaveBalance leaveBalance) {
        LeaveBalanceDto dto = new LeaveBalanceDto();
        dto.setId(leaveBalance.getId());
        dto.setUserId(leaveBalance.getUserId());
        dto.setLeaveTypeId(leaveBalance.getLeaveTypeId());
        dto.setEntitlement(leaveBalance.getEntitlement());
        dto.setUsed(leaveBalance.getUsed());
        dto.setRemaining(leaveBalance.getRemaining());
        return dto;
    }

    public LeaveBalance mapToLeaveBalance(LeaveBalanceDto leaveBalanceDto) {
        LeaveBalance leaveBalance = new LeaveBalance();
        leaveBalance.setId(leaveBalanceDto.getId());
        leaveBalance.setUserId(leaveBalanceDto.getUserId());
        leaveBalance.setLeaveTypeId(leaveBalanceDto.getLeaveTypeId());
        leaveBalance.setEntitlement(leaveBalanceDto.getEntitlement());
        leaveBalance.setUsed(leaveBalanceDto.getUsed());
        leaveBalance.setRemaining(leaveBalanceDto.getRemaining());
        return leaveBalance;
    }

    // User Mapping
    public UserDto mapToUserDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(lookupService.getRoleNameByUserId(user.getId()));
        dto.setProfilePicture(user.getProfilePicture());
        return dto;
    }

    public User mapToUser(UserDto userDto) {
        User user = new User();
        // user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setProfilePicture(userDto.getProfilePicture());
        return user;
    }

    public UserRole mapToUserRole(UserRoleDto userRoleDto) {
        UserRole userRole = new UserRole();
        // userRole.setId(userRoleDto.getId());
        userRole.setUserId(userRoleDto.getUserId());
        userRole.setRoleId(userRoleDto.getRoleId());
        // userRole.setRoleName(userRoleDto.getRoleName());
        return userRole;
    }

    public UserRoleDto mapToUserRoleDto(UserRole userRole) {
        UserRoleDto dto = new UserRoleDto();
        // dto.setId(userRole.getId());
        dto.setUserId(userRole.getUserId());
        dto.setRoleId(userRole.getRoleId());
        dto.setRoleName(lookupService.getRoleNameById(userRole.getRoleId()));
        return dto;
    }

    // LeaveRequest Mapping
    public LeaveRequestDto mapToLeaveRequestDto(LeaveRequest leaveRequest, String userName) {
        LeaveRequestDto dto = new LeaveRequestDto();
        dto.setId(leaveRequest.getId());
        dto.setUserId(leaveRequest.getUserId());
        dto.setLeaveTypeId(leaveRequest.getLeaveTypeId());
        dto.setStartDate(leaveRequest.getStartDate());
        dto.setEndDate(leaveRequest.getEndDate());
        dto.setReason(leaveRequest.getReason());
        dto.setComment(leaveRequest.getComment());
        dto.setStatus(leaveRequest.getStatus());
        dto.setHasDocuments(leaveRequest.isHasDocuments()); // Assuming default value
        dto.setLeaveTypeName(lookupService.getLeaveTypeNameById(leaveRequest.getLeaveTypeId()));
        dto.setUserName(userName);
        return dto;
    }

    public LeaveRequest mapToLeaveRequest(LeaveRequestDto leaveRequestDto) {
        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setId(leaveRequestDto.getId());
        leaveRequest.setUserId(leaveRequestDto.getUserId());
        leaveRequest.setLeaveTypeId(leaveRequestDto.getLeaveTypeId());
        leaveRequest.setStartDate(leaveRequestDto.getStartDate());
        leaveRequest.setEndDate(leaveRequestDto.getEndDate());
        leaveRequest.setReason(leaveRequestDto.getReason());
        leaveRequest.setComment(leaveRequestDto.getComment());
        leaveRequest.setHasDocuments(leaveRequestDto.isHasDocuments()); // Assuming default value
        leaveRequest.setStatus(leaveRequestDto.getStatus());
        return leaveRequest;
    }

    // LeaveType Mapping
    public LeaveTypeDto mapToLeaveTypeDto(LeaveType leaveType) {
        LeaveTypeDto dto = new LeaveTypeDto();
        dto.setId(leaveType.getId());
        dto.setName(leaveType.getName());
        dto.setDefaultDays(leaveType.getDefaultDays());
        return dto;
    }

    public LeaveType mapToLeaveType(LeaveTypeDto leaveTypeDto) {
        LeaveType leaveType = new LeaveType();
        leaveType.setId(leaveTypeDto.getId());
        leaveType.setName(leaveTypeDto.getName());
        leaveType.setDefaultDays(leaveTypeDto.getDefaultDays());
        return leaveType;
    }

    // Notification Mapping
    public NotificationDto mapToNotificationDto(Notification notification) {
        NotificationDto dto = new NotificationDto();
        dto.setId(notification.getId());
        dto.setRecipientId(notification.getRecipientId());
        dto.setType(notification.getType());
        dto.setMessage(notification.getMessage());
        dto.setRead(notification.isRead());
        dto.setTimestamp(notification.getTimestamp());
        return dto;
    }

    public Notification mapToNotification(NotificationDto notificationDto) {
        Notification notification = new Notification();
        notification.setId(notificationDto.getId());
        notification.setMessage(notificationDto.getMessage());
        notification.setRecipientId(notificationDto.getRecipientId());
        notification.setType(notificationDto.getType());
        notification.setRead(notificationDto.isRead());
        notification.setTimestamp(notificationDto.getTimestamp());
        return notification;
    }

    // Role Mapping
    public RoleDto mapToRoleDto(Role role) {
        RoleDto dto = new RoleDto();
        dto.setId(role.getId());
        dto.setName(role.getName());
        return dto;
    }

    public Role mapToRole(RoleDto roleDto) {
        Role role = new Role();
        role.setId(roleDto.getId());
        role.setName(roleDto.getName());
        return role;
    }

    // Add similar mapping methods for other classes as needed
}