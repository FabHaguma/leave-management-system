package com.ist.leavemanagementsystem.controller;

import com.ist.leavemanagementsystem.dto.LeaveTypeDto;
import com.ist.leavemanagementsystem.service.LeaveTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leave-types")
public class LeaveTypeController {

    @Autowired
    private LeaveTypeService leaveTypeService;

    // List all leave types
    @GetMapping
    public ResponseEntity<List<LeaveTypeDto>> getAllLeaveTypes() {
        List<LeaveTypeDto> leaveTypes = leaveTypeService.getAllLeaveTypes();
        return ResponseEntity.ok(leaveTypes);
    }

    // Insert a new leave type
    @PostMapping
    public ResponseEntity<LeaveTypeDto> createLeaveType(@RequestBody LeaveTypeDto leaveTypeDto) {
        LeaveTypeDto createdLeaveType = leaveTypeService.createLeaveType(leaveTypeDto);
        return ResponseEntity.status(201).body(createdLeaveType); // Return 201 Created
    }
}
