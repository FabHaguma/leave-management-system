package com.ist.leavemanagementsystem.service;

import com.ist.leavemanagementsystem.dto.LeaveBalanceDto;
import com.ist.leavemanagementsystem.dto.LeaveTypeDto;
import com.ist.leavemanagementsystem.dto.PolicyDto;

import java.util.List;

public interface AdminService {
    void adjustUserLeaveBalances(Long userId, List<LeaveBalanceDto> balances);

    LeaveTypeDto createLeaveType(LeaveTypeDto dto);

    LeaveTypeDto updateLeaveType(Long id, LeaveTypeDto dto);

    void deleteLeaveType(Long id);

    void updatePolicy(PolicyDto policyDto);

    byte[] exportReport(String format);
}