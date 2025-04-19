package com.ist.leavemanagementsystem.service.impl;

import com.ist.leavemanagementsystem.model.*;
import com.ist.leavemanagementsystem.service.NotificationService;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {
    // Inject NotificationRepository, EmailService, etc.

    @Override
    public void notifyLeaveSubmitted(User user, LeaveRequest leaveRequest) {
        // Create and save notification, send email if needed
    }

    @Override
    public void notifyLeaveApproved(User user, LeaveRequest leaveRequest) {
        // Notify user of approval
    }

    @Override
    public void notifyLeaveRejected(User user, LeaveRequest leaveRequest) {
        // Notify user of rejection
    }

    @Override
    public void notifyPendingApproval(User approver, LeaveRequest leaveRequest) {
        // Notify manager/admin to approve/reject
    }

    @Override
    public void notifyLeaveStatusChanged(User user, LeaveRequest leaveRequest, String status) {
        // Notify user of status change (e.g., APPROVED, REJECTED, PENDING, etc.)
    }
}