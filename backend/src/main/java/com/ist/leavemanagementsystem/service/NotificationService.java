package com.ist.leavemanagementsystem.service;

import com.ist.leavemanagementsystem.model.User;
import com.ist.leavemanagementsystem.model.LeaveRequest;

public interface NotificationService {
    void notifyLeaveSubmitted(User user, LeaveRequest leaveRequest);

    void notifyLeaveApproved(User user, LeaveRequest leaveRequest);

    void notifyLeaveRejected(User user, LeaveRequest leaveRequest);

    void notifyPendingApproval(LeaveRequest leaveRequest);

    void notifyLeaveStatusChanged(User user, LeaveRequest leaveRequest, String status);
}