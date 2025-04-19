package com.ist.leavemanagementsystem.service;

import com.ist.leavemanagementsystem.model.LeaveBalance;
import com.ist.leavemanagementsystem.model.LeaveType;
import com.ist.leavemanagementsystem.model.User;
import com.ist.leavemanagementsystem.dto.LeaveBalanceDto;
import java.util.List;

public interface LeaveBalanceService {
    void accrueMonthlyLeave();

    void processCarryForward();

    LeaveBalance getLeaveBalance(User user, LeaveType type);

    List<LeaveBalance> getUserLeaveBalances(User user);

    boolean hasSufficientEntitlement(User user, LeaveType type, double daysRequested);

    void updateLeaveBalanceOnRequest(User user, LeaveType type, double days);

    void adjustLeaveBalance(User user, LeaveType type, double days);

    void updateUserLeaveBalances(Long userId, List<LeaveBalanceDto> balances);
}