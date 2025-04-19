package com.ist.leavemanagementsystem.controller;

import com.ist.leavemanagementsystem.dto.LeaveRequestDto;
import com.ist.leavemanagementsystem.service.LeaveRequestService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leave-requests")
public class LeaveRequestController {
    private final LeaveRequestService leaveRequestService;

    public LeaveRequestController(LeaveRequestService leaveRequestService) {
        this.leaveRequestService = leaveRequestService;
    }

    // Submit a new leave application
    @PostMapping
    public LeaveRequestDto createLeaveRequest(@RequestBody LeaveRequestDto leaveRequestDto,
            @AuthenticationPrincipal OAuth2User principal) {
        return leaveRequestService.submitLeaveRequest(leaveRequestDto, principal);
    }

    // Get the logged-in user's leave requests
    @GetMapping("/me")
    public List<LeaveRequestDto> getMyLeaveRequests(@AuthenticationPrincipal OAuth2User principal) {
        return leaveRequestService.getUserLeaveRequests(principal);
    }

    // (Admin/Manager) Get all leave requests for approval
    @GetMapping("/pending")
    public List<LeaveRequestDto> getPendingRequests() {
        return leaveRequestService.getPendingLeaveRequests();
    }

    // Get a leave request by ID
    @GetMapping("/{id}")
    public LeaveRequestDto getLeaveRequest(@PathVariable Long id) {
        return leaveRequestService.getLeaveRequestById(id);
    }

    // Update (edit) a leave request by ID (if allowed)
    @PutMapping("/{id}")
    public LeaveRequestDto updateLeaveRequest(@PathVariable Long id, @RequestBody LeaveRequestDto leaveRequestDto) {
        return leaveRequestService.updateLeaveRequest(id, leaveRequestDto);
    }

    // Delete a leave request by ID (if allowed)
    @DeleteMapping("/{id}")
    public void deleteLeaveRequest(@PathVariable Long id) {
        leaveRequestService.deleteLeaveRequest(id);
    }
}