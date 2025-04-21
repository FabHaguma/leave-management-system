package com.ist.leavemanagementsystem.service.impl;

import com.ist.leavemanagementsystem.model.*;
import com.ist.leavemanagementsystem.dto.LeaveBalanceDto;
import com.ist.leavemanagementsystem.repository.LeaveBalanceRepository;
import com.ist.leavemanagementsystem.repository.LeaveTypeRepository;
import com.ist.leavemanagementsystem.repository.UserRepository;
import com.ist.leavemanagementsystem.service.LeaveBalanceService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LeaveBalanceServiceImpl implements LeaveBalanceService {
    @Autowired
    private LeaveBalanceRepository leaveBalanceRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LeaveTypeRepository leaveTypeRepository;

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
        // TODO: Implement logic to find specific balance by user and type
        // Example: return leaveBalanceRepository.findByUserAndLeaveType(user,
        // type).orElse(null);
        // Need to add findByUserAndLeaveType method to LeaveBalanceRepository
        return null;
    }

    @Override
    public List<LeaveBalance> getUserLeaveBalances(User user) {
        // TODO: Implement logic to find all balances for a user
        // Example: return leaveBalanceRepository.findByUser(user);
        // Need to add findByUser method to LeaveBalanceRepository
        return leaveBalanceRepository.findAll().stream()
                .filter(lb -> lb.getUser() != null && lb.getUser().getId().equals(user.getId()))
                .toList();
    }

    @Override
    public boolean hasSufficientEntitlement(User user, LeaveType type, double daysRequested) {
        LeaveBalance balance = getLeaveBalance(user, type);
        // TODO: Handle case where balance might be null
        return balance != null && balance.getRemaining() >= daysRequested;
    }

    @Override
    @Transactional
    public void updateLeaveBalanceOnRequest(User user, LeaveType type, double days) {
        LeaveBalance balance = getLeaveBalance(user, type);
        if (balance != null) {
            balance.setUsed(balance.getUsed() + days);
            balance.setRemaining(balance.getEntitlement() - balance.getUsed());
            leaveBalanceRepository.save(balance);
        } else {
            throw new RuntimeException(
                    "Leave balance not found for user " + user.getId() + " and leave type " + type.getId());
        }
    }

    @Override
    @Transactional
    public void adjustLeaveBalance(User user, LeaveType type, double days) {
        LeaveBalance balance = getLeaveBalance(user, type);
        if (balance != null) {
            balance.setEntitlement(balance.getEntitlement() + days);
            balance.setRemaining(balance.getEntitlement() - balance.getUsed());
            leaveBalanceRepository.save(balance);
        } else {
            throw new RuntimeException(
                    "Leave balance not found for user " + user.getId() + " and leave type " + type.getId());
        }
    }

    @Override
    @Transactional
    public void updateUserLeaveBalances(Long userId, List<LeaveBalanceDto> balances) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found for id: " + userId));

        for (LeaveBalanceDto dto : balances) {
            LeaveBalance leaveBalance = Optional.ofNullable(dto.getId())
                    .flatMap(leaveBalanceRepository::findById)
                    .orElse(new LeaveBalance());

            LeaveType leaveType = leaveTypeRepository.findById(dto.getLeaveTypeId())
                    .orElseThrow(
                            () -> new EntityNotFoundException("LeaveType not found for id: " + dto.getLeaveTypeId()));

            leaveBalance.setUser(user);
            leaveBalance.setLeaveType(leaveType);
            leaveBalance.setEntitlement(dto.getEntitlement());
            leaveBalance.setUsed(dto.getUsed());
            leaveBalance.setRemaining(dto.getRemaining());

            leaveBalanceRepository.save(leaveBalance);
        }
    }
}