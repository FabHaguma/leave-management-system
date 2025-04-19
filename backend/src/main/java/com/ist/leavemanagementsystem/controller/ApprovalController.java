package com.ist.leavemanagementsystem.controller;

import com.ist.leavemanagementsystem.dto.LeaveApprovalDto;
import com.ist.leavemanagementsystem.service.ApprovalService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/approvals")
public class ApprovalController {

    private final ApprovalService approvalService;

    public ApprovalController(ApprovalService approvalService) {
        this.approvalService = approvalService;
    }

    // Approve a leave request
    @PostMapping("/{leaveRequestId}/approve")
    public void approveLeave(@PathVariable Long leaveRequestId, @RequestBody LeaveApprovalDto approvalDto) {
        approvalService.approveLeaveRequest(leaveRequestId, approvalDto);
    }

    // Reject a leave request, with comment
    @PostMapping("/{leaveRequestId}/reject")
    public void rejectLeave(@PathVariable Long leaveRequestId, @RequestBody LeaveApprovalDto approvalDto) {
        approvalService.rejectLeaveRequest(leaveRequestId, approvalDto);
    }
}