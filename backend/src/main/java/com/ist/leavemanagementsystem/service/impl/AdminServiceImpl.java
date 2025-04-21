package com.ist.leavemanagementsystem.service.impl;

import com.ist.leavemanagementsystem.dto.LeaveBalanceDto;
import com.ist.leavemanagementsystem.dto.LeaveTypeDto;
import com.ist.leavemanagementsystem.dto.PolicyDto;
import com.ist.leavemanagementsystem.model.LeaveBalance;
import com.ist.leavemanagementsystem.model.LeaveType;
import com.ist.leavemanagementsystem.repository.LeaveBalanceRepository;
import com.ist.leavemanagementsystem.repository.LeaveTypeRepository;
import com.ist.leavemanagementsystem.repository.UserRepository; // Assuming needed for user validation or context
import com.ist.leavemanagementsystem.service.AdminService;
import jakarta.persistence.EntityNotFoundException; // For handling not found cases
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // For transactional operations

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private static final Logger log = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Autowired
    private UserRepository userRepository; // Assuming needed for user validation

    @Autowired
    private LeaveBalanceRepository leaveBalanceRepository;

    @Autowired
    private LeaveTypeRepository leaveTypeRepository;

    @Override
    @Transactional // Ensure atomicity
    public void adjustUserLeaveBalances(Long userId, List<LeaveBalanceDto> balances) {
        log.info("Adjusting leave balances for user {}", userId);
        // Basic validation: Check if user exists
        userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        for (LeaveBalanceDto balanceDto : balances) {
            // Find existing balance or create a new one if needed (depends on requirements)
            // This example assumes balance must exist to be adjusted
            LeaveBalance balance = leaveBalanceRepository
                    .findByUserIdAndLeaveTypeId(userId, balanceDto.getLeaveTypeId())
                    .orElseThrow(() -> new EntityNotFoundException("Leave balance not found for user " + userId
                            + " and leave type " + balanceDto.getLeaveTypeId()));

            // Use entitlement instead of balance
            balance.setEntitlement(balanceDto.getEntitlement());
            leaveBalanceRepository.save(balance);
            // Update log message to use entitlement
            log.debug("Updated entitlement for user {} and leave type {}: {}", userId, balanceDto.getLeaveTypeId(),
                    balanceDto.getEntitlement());
        }
        log.info("Successfully adjusted leave balances for user {}", userId);
    }

    @Override
    @Transactional
    public LeaveTypeDto createLeaveType(LeaveTypeDto dto) {
        log.info("Creating new leave type: {}", dto.getName());
        LeaveType leaveType = new LeaveType();
        leaveType.setName(dto.getName());
        leaveType.setDefaultDays(dto.getDefaultDays());
        // Add other properties if needed from DTO

        LeaveType savedLeaveType = leaveTypeRepository.save(leaveType);
        log.info("Successfully created leave type with ID {}", savedLeaveType.getId());

        // Convert saved entity back to DTO
        return new LeaveTypeDto(savedLeaveType.getId(), savedLeaveType.getName(), savedLeaveType.getDefaultDays());
    }

    @Override
    @Transactional
    public LeaveTypeDto updateLeaveType(Long id, LeaveTypeDto dto) {
        log.info("Updating leave type with ID {}", id);
        LeaveType leaveType = leaveTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("LeaveType not found with id: " + id));

        leaveType.setName(dto.getName());
        leaveType.setDefaultDays(dto.getDefaultDays());
        // Update other properties if needed

        LeaveType updatedLeaveType = leaveTypeRepository.save(leaveType);
        log.info("Successfully updated leave type with ID {}", id);

        return new LeaveTypeDto(updatedLeaveType.getId(), updatedLeaveType.getName(),
                updatedLeaveType.getDefaultDays());
    }

    @Override
    @Transactional
    public void deleteLeaveType(Long id) {
        log.info("Deleting leave type with ID {}", id);
        if (!leaveTypeRepository.existsById(id)) {
            throw new EntityNotFoundException("LeaveType not found with id: " + id);
        }
        // Consider adding checks for dependencies (e.g., existing leave requests of
        // this type)
        leaveTypeRepository.deleteById(id);
        log.info("Successfully deleted leave type with ID {}", id);
    }

    @Override
    public void updatePolicy(PolicyDto policyDto) {
        // Basic implementation: Just log the policy update request
        // A real implementation would involve storing/updating policy data,
        // potentially in a dedicated table or configuration file.
        log.info("Received request to update policy: {}", policyDto);
        // Example: policyRepository.save(convertDtoToPolicyEntity(policyDto));
        log.warn("Policy update logic is not fully implemented.");
    }

    @Override
    public byte[] exportReport(String format) {
        // Basic implementation: Return a simple placeholder message or CSV header
        log.info("Generating report in format: {}", format);
        String content;
        if ("csv".equalsIgnoreCase(format)) {
            content = "UserId,UserName,LeaveType,StartDate,EndDate,Status\\n"; // Example CSV Header
        } else if ("excel".equalsIgnoreCase(format)) {
            // Excel generation would require a library like Apache POI
            content = "Excel report generation not implemented.";
            log.warn("Excel report generation requires additional libraries/implementation.");
        } else {
            content = "Unsupported report format: " + format;
            log.warn("Unsupported report format requested: {}", format);
        }
        log.info("Report generation placeholder executed.");
        return content.getBytes(); // Return simple string as bytes
    }
}