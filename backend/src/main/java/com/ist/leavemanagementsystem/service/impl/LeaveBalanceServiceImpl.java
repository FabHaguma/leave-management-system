package com.ist.leavemanagementsystem.service.impl;

import com.ist.leavemanagementsystem.model.*;
import com.ist.leavemanagementsystem.dto.LeaveBalanceDto;
import com.ist.leavemanagementsystem.repository.LeaveBalanceRepository;
import com.ist.leavemanagementsystem.repository.UserRepository;
import com.ist.leavemanagementsystem.service.LeaveBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LeaveBalanceServiceImpl implements LeaveBalanceService {
    @Autowired
    private LeaveBalanceRepository leaveBalanceRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void accrueMonthlyLeave() {
        // For each user and leave type, add monthly accrual rate to entitlement
        // Example: if accrual_rate_per_month = 1.66, add to entitlement and update
        // remaining
    }

    @Override
    public void processCarryForward() {
        // At year end, carry forward up to max days, expire excess - per policy
    }

    @Override
    public LeaveBalance getLeaveBalance(User user, LeaveType type) {
        // Query repository
        return null;
    }

    @Override
    public List<LeaveBalance> getUserLeaveBalances(User user) {
        // Query repository
        return null;
    }

    @Override
    public boolean hasSufficientEntitlement(User user, LeaveType type, double daysRequested) {
        // Check if remaining >= daysRequested
        return false;
    }

    @Override
    public void updateLeaveBalanceOnRequest(User user, LeaveType type, double days) {
        // Deduct used days from remaining
    }

    @Override
    public void adjustLeaveBalance(User user, LeaveType type, double days) {
        // Manual adjustment
    }

    @Override
    public void updateUserLeaveBalances(Long userId, List<LeaveBalanceDto> balances) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty())
            throw new RuntimeException("User not found for id: " + userId);
        User user = userOpt.get();
        for (LeaveBalanceDto dto : balances) {
            LeaveBalance leaveBalance = leaveBalanceRepository.findById(dto.getId()).orElse(new LeaveBalance());
            leaveBalance.setUser(user);
            leaveBalance.setYear(dto.getYear());
            leaveBalance.setTotalEntitlement(dto.getTotalEntitlement());
            leaveBalance.setDaysUsed(dto.getDaysUsed());
            leaveBalanceRepository.save(leaveBalance);
        }
    }
}