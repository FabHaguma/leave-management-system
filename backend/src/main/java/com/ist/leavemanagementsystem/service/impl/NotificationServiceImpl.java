package com.ist.leavemanagementsystem.service.impl;

import com.ist.leavemanagementsystem.model.*;
import com.ist.leavemanagementsystem.service.NotificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ist.leavemanagementsystem.repository.RoleRepository;
import com.ist.leavemanagementsystem.repository.UserRepository;
import com.ist.leavemanagementsystem.repository.NotificationRepository;
import com.ist.leavemanagementsystem.util.MapperUtil;

@Service
public class NotificationServiceImpl implements NotificationService {
    // Inject NotificationRepository, EmailService, etc.

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public void notifyLeaveSubmitted(User user, LeaveRequest leaveRequest) {
        // Create and save notification, send email if needed
    }

    @Override
    public void notifyLeaveApproved(User user, LeaveRequest leaveRequest) {
        Notification notification = new Notification();
        notification.setRecipientId(user.getId());
        notification.setMessage("Your leave request from " + leaveRequest.getStartDate() + " to " +
                leaveRequest.getEndDate() + " has been approved.");
        notification.setType("APPROVED");

        notificationRepository.save(notification);
    }

    @Override
    public void notifyLeaveRejected(User user, LeaveRequest leaveRequest) {
        Notification notification = new Notification();
        notification.setRecipientId(user.getId());
        notification.setMessage("Your leave request from " + leaveRequest.getStartDate() + " to " +
                leaveRequest.getEndDate() + " has been rejected.");
        notification.setType("REJECTED");

        notificationRepository.save(notification);
    }

    @Override
    public void notifyPendingApproval(LeaveRequest leaveRequest) {
        Role approverRole = roleRepository.findByName("ADMIN");
        User approverUser = userRepository.findById(approverRole.getId()).orElse(null);
        User user = userRepository.findById(leaveRequest.getUserId()).orElse(null);

        if (approverUser != null) {
            Notification notification = new Notification();
            notification.setRecipientId(approverUser.getId());
            notification.setMessage("Leave request pending approval for " + user.getName() + " from " +
                    leaveRequest.getStartDate() + " to " + leaveRequest.getEndDate());
            notification.setType("LEAVE_SUBMITTED");

            notificationRepository.save(notification);
        }
    }

    @Override
    public void notifyLeaveStatusChanged(User user, LeaveRequest leaveRequest, String status) {
        // Notify user of status change (e.g., APPROVED, REJECTED, PENDING, etc.)
    }
}