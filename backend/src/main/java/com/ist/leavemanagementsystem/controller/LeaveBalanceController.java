package com.ist.leavemanagementsystem.controller;

import com.ist.leavemanagementsystem.dto.LeaveBalanceDto;
import com.ist.leavemanagementsystem.dto.LeaveTypeDto;
import com.ist.leavemanagementsystem.model.LeaveBalance;
import com.ist.leavemanagementsystem.service.LeaveBalanceService;
import com.ist.leavemanagementsystem.service.LeaveTypeService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.ist.leavemanagementsystem.util.MapperUtil;

import java.util.List;

@RestController
@RequestMapping("/api/leave-balances")
public class LeaveBalanceController {

    @Autowired
    private LeaveBalanceService leaveBalanceService;
    @Autowired
    private MapperUtil mapperUtil;
    @Autowired
    private LeaveTypeService leaveTypeService;

    // Get current user's leave balances
    @GetMapping("/me/{id}")
    public List<LeaveBalanceDto> getMyLeaveBalances(@PathVariable() Long id) {
        List<LeaveBalance> balances = leaveBalanceService.getUserLeaveBalances(id);

        // get leave balance dto adding the leave type name
        return balances.stream().map(balance -> mapperUtil.mapToLeaveBalanceDto(balance)).toList();
    }

    @GetMapping("/all")
    public ResponseEntity<List<LeaveBalanceDto>> getAllLeaveBalances() {
        List<LeaveBalanceDto> balanceDtoList = leaveBalanceService.getAllLeaveBalances();
        List<LeaveTypeDto> typeDtoList = leaveTypeService.getAllLeaveTypes(); // Assuming this method exists

        // Add leave type name to each balance DTO
        for (LeaveBalanceDto balanceDto : balanceDtoList) {
            for (LeaveTypeDto leaveType : typeDtoList) {
                if (balanceDto.getLeaveTypeId().equals(leaveType.getId())) {
                    balanceDto.setLeaveTypeName(leaveType.getName());
                }
            }
        }
        return ResponseEntity.ok(balanceDtoList);
    }

    // (Admin) Update leave balance for a user - This endpoint might need role
    // checks later
    @PutMapping("/admin-update")
    public ResponseEntity<Void> updateLeaveBalance(@RequestBody List<LeaveBalanceDto> balances) {
        leaveBalanceService.updateUserLeaveBalances(balances);
        return ResponseEntity.noContent().build();
    }
}