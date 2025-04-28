package com.ist.leavemanagementsystem.controller;

import com.ist.leavemanagementsystem.dto.LeaveBalanceDto;
import com.ist.leavemanagementsystem.dto.LeaveTypeDto;
import com.ist.leavemanagementsystem.dto.PolicyDto;
import com.ist.leavemanagementsystem.service.AdminService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // Adjust leave balances for a user
    @PutMapping("/users/{userId}/leave-balances")
    public void adjustUserLeaveBalances(@PathVariable Long userId, @RequestBody List<LeaveBalanceDto> balances) {
        adminService.adjustUserLeaveBalances(userId, balances);
    }

    // Manage leave types (CRUD)
    @PostMapping("/leave-types")
    public LeaveTypeDto createLeaveType(@RequestBody LeaveTypeDto dto) {
        return adminService.createLeaveType(dto);
    }

    @PutMapping("/leave-types/{id}")
    public LeaveTypeDto updateLeaveType(@PathVariable Long id, @RequestBody LeaveTypeDto dto) {
        return adminService.updateLeaveType(id, dto);
    }

    @DeleteMapping("/leave-types/{id}")
    public void deleteLeaveType(@PathVariable Long id) {
        adminService.deleteLeaveType(id);
    }

    // Manage policies
    @PutMapping("/policies")
    public void updatePolicy(@RequestBody PolicyDto policyDto) {
        adminService.updatePolicy(policyDto);
    }

    // Export reports (CSV/Excel)
    @GetMapping("/reports")
    public byte[] exportReport(@RequestParam(required = false, defaultValue = "csv") String format) {
        return adminService.exportReport(format);
    }
}