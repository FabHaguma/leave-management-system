package com.ist.leavemanagementsystem.service.impl;

import com.ist.leavemanagementsystem.model.*;
import com.ist.leavemanagementsystem.dto.LeaveBalanceDto;
import com.ist.leavemanagementsystem.repository.LeaveBalanceRepository;
import com.ist.leavemanagementsystem.repository.LeaveTypeRepository;
import com.ist.leavemanagementsystem.service.LeaveBalanceService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ist.leavemanagementsystem.util.MapperUtil;

import java.util.List;

@Service
public class LeaveBalanceServiceImpl implements LeaveBalanceService {

    @Autowired
    private LeaveBalanceRepository leaveBalanceRepository;
    @Autowired
    private LeaveTypeRepository leaveTypeRepository;
    @Autowired
    private MapperUtil mapperUtil;

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
    public List<LeaveBalance> getUserLeaveBalances(Long userId) {
        return leaveBalanceRepository.findByUserId(userId);
    }

    @Override
    public boolean hasSufficientBalance(long userId, Long leaveTypeId, int daysRequested) {
        List<LeaveBalance> balances = leaveBalanceRepository.findByUserId(userId);
        if (balances.isEmpty()) {
            return false; // No leave balances found for the user
        }
        // add up all days used for the leave type id we have
        int remainingPlusEntitled = 0;
        boolean isFirstTimeUser = false;
        for (LeaveBalance balance : balances) {
            if (balance.getLeaveTypeId().equals(leaveTypeId)) {
                remainingPlusEntitled = balance.getRemaining() + balance.getEntitlement();
                // for the first time user will have zero on all fields
                if (balance.getRemaining() == 0 && balance.getUsed() == 0) {
                    // we need to check the default days of the leave type
                    isFirstTimeUser = true;
                }
            }
        }

        if (isFirstTimeUser) {
            LeaveType leaveType = leaveTypeRepository.findById(leaveTypeId)
                    .orElseThrow(() -> new EntityNotFoundException("LeaveType not found for id: " + leaveTypeId));

            remainingPlusEntitled += leaveType.getDefaultDays();
        }
        // Compare days requested with total days available
        return remainingPlusEntitled >= daysRequested;
    }

    @Override
    @Transactional
    public void adjustLeaveBalance(Long userId, Long leaveTypeId, int days) {
        LeaveType leaveType = leaveTypeRepository.findById(leaveTypeId)
                .orElseThrow(() -> new EntityNotFoundException("LeaveType not found for id: " + leaveTypeId));
        LeaveBalance balance = leaveBalanceRepository.findByUserIdAndLeaveTypeId(userId, leaveTypeId)
                .orElse(null);

        if (balance != null) {
            balance.setUsed(balance.getUsed() + days);
            balance.setRemaining(leaveType.getDefaultDays() - balance.getUsed());
            leaveBalanceRepository.save(balance);
        } else {
            throw new RuntimeException(
                    "Leave balance not found for user " + userId + " and leave type " + leaveTypeId);
        }
    }

    @Override
    @Transactional
    public void updateUserLeaveBalances(List<LeaveBalanceDto> leaveBalances) {
        for (LeaveBalanceDto dto : leaveBalances) {
            LeaveBalance balance = leaveBalanceRepository
                    .findByUserIdAndLeaveTypeId(dto.getUserId(), dto.getLeaveTypeId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Leave balance not found for user " + dto.getUserId() + " and leave type "
                                    + dto.getLeaveTypeId()));

            balance.setUsed(dto.getUsed());
            balance.setEntitlement(dto.getEntitlement());
            balance.setRemaining(dto.getRemaining());
            leaveBalanceRepository.save(balance);
        }
    }

    @Override
    public List<LeaveBalanceDto> getAllLeaveBalances() {
        List<LeaveBalance> balances = leaveBalanceRepository.findAll();
        return balances.stream().map(leaveBalance -> mapperUtil.mapToLeaveBalanceDto(leaveBalance)).toList();
    }
}