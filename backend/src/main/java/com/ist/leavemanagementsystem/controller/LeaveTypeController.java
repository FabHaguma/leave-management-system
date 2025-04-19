package com.ist.leavemanagementsystem.controller;

import com.ist.leavemanagementsystem.dto.LeaveTypeDto;
import com.ist.leavemanagementsystem.service.LeaveTypeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leave-types")
public class LeaveTypeController {

    private final LeaveTypeService leaveTypeService;

    public LeaveTypeController(LeaveTypeService leaveTypeService) {
        this.leaveTypeService = leaveTypeService;
    }

    // List all leave types
    @GetMapping
    public List<LeaveTypeDto> getAllLeaveTypes() {
        return leaveTypeService.getAllLeaveTypes();
    }
}
