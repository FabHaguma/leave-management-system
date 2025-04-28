package com.ist.leavemanagementsystem.repository;

import com.ist.leavemanagementsystem.model.LeaveBalance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, Long> {

    Optional<LeaveBalance> findByUserIdAndLeaveTypeId(Long userId, Long leaveTypeId);

    List<LeaveBalance> findByUserId(Long userId); // Added method to find by userId only

    Optional<LeaveBalance> findByLeaveTypeId(Long leaveTypeId); // Added method to find by leaveTypeId only
}
