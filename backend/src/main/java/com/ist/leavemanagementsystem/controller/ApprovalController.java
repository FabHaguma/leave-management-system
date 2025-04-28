package com.ist.leavemanagementsystem.controller;

import com.ist.leavemanagementsystem.dto.LeaveApprovalDto;
import com.ist.leavemanagementsystem.service.ApprovalService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/approvals")
public class ApprovalController {
    @Autowired
    private ApprovalService approvalService;

    // Approve a leave request
    @PostMapping("/approve")
    public void approveLeave(@RequestBody LeaveApprovalDto approvalDto) {
        approvalService.approveLeaveRequest(approvalDto.getLeaveRequestId(), approvalDto);
    }

    // Reject a leave request, with comment
    @PostMapping("/reject")
    public void rejectLeave(@RequestBody LeaveApprovalDto approvalDto) {
        approvalService.rejectLeaveRequest(approvalDto.getLeaveRequestId(), approvalDto);
    }
}