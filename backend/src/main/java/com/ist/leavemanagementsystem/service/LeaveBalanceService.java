package com.ist.leavemanagementsystem.service;

import com.ist.leavemanagementsystem.dto.LeaveBalanceDto;
import com.ist.leavemanagementsystem.model.LeaveBalance;
import java.util.List;

public interface LeaveBalanceService {
    void accrueMonthlyLeave();

    void processCarryForward();

    List<LeaveBalance> getUserLeaveBalances(Long userId);

    List<LeaveBalanceDto> getAllLeaveBalances();

    boolean hasSufficientBalance(long userId, Long leaveTypeId, int daysRequested);

    void adjustLeaveBalance(Long userId, Long leaveTypeId, int days);

    void updateUserLeaveBalances(List<LeaveBalanceDto> leaveBalances);

}