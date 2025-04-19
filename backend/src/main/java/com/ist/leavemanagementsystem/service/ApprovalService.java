package com.ist.leavemanagementsystem.service;

import com.ist.leavemanagementsystem.dto.LeaveApprovalDto;
import org.springframework.stereotype.Service;

@Service
public class ApprovalService {
    public void approveLeaveRequest(Long leaveRequestId, LeaveApprovalDto approvalDto) {
        // TODO: Implement logic to approve the leave request
    }

    public void rejectLeaveRequest(Long leaveRequestId, LeaveApprovalDto approvalDto) {
        // TODO: Implement logic to reject the leave request
    }
}
