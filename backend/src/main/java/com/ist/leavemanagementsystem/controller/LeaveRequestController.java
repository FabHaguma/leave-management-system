package com.ist.leavemanagementsystem.controller;

import com.ist.leavemanagementsystem.dto.LeaveRequestDto;
import com.ist.leavemanagementsystem.service.LeaveRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/leave-requests")
public class LeaveRequestController {
    @Autowired
    private LeaveRequestService leaveRequestService;

    // Submit a new leave application
    // @PostMapping
    // public LeaveRequestDto createLeaveRequest(@RequestBody LeaveRequestDto
    // leaveRequestDto) {
    // return leaveRequestService.submitLeaveRequest(leaveRequestDto);
    // }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<?> submitLeaveRequest(
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate,
            @RequestParam("leaveTypeId") Long leaveTypeId,
            @RequestParam("reason") String reason,
            @RequestParam("userId") Long userId,
            @RequestParam("leaveTypeName") String leaveTypeName,
            @RequestParam("hasDocuments") Boolean hasDocuments,
            @RequestParam(value = "supportingDocuments", required = false) MultipartFile[] supportingDocuments) {

        LeaveRequestDto leaveRequestDto = new LeaveRequestDto();
        leaveRequestDto.setStartDate(startDate);
        leaveRequestDto.setEndDate(endDate);
        leaveRequestDto.setLeaveTypeId(leaveTypeId);
        leaveRequestDto.setReason(reason);
        leaveRequestDto.setUserId(userId);
        leaveRequestDto.setLeaveTypeName(leaveTypeName);

        leaveRequestService.submitLeaveRequest(leaveRequestDto, supportingDocuments);

        if (supportingDocuments != null) {
            for (MultipartFile file : supportingDocuments) {
                System.out.println("Received file: " + file.getOriginalFilename());
            }
        }

        // Return a response
        return ResponseEntity.ok("Leave request submitted successfully");
    }

    // Get the logged-in user's leave requests
    @GetMapping("/my-history/{id}")
    public ResponseEntity<List<LeaveRequestDto>> getLeaveHistory(@PathVariable() Long id) {
        List<LeaveRequestDto> leaveRequests = leaveRequestService.getUserLeaveRequests(id);
        if (leaveRequests.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(leaveRequests);
    }

    @GetMapping("/leave-status/{id}")
    public ResponseEntity<LeaveRequestDto> checkLeaveApplicationStatus(@PathVariable() Long id) {
        LeaveRequestDto leaveRequests = leaveRequestService.checkLeaveApplicationStatus(id);
        if (leaveRequests == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(leaveRequests);
    }

    // (Admin/Manager) Get all leave requests for approval
    @GetMapping("/pending")
    public ResponseEntity<List<LeaveRequestDto>> getPendingRequests() {
        List<LeaveRequestDto> list = leaveRequestService.getPendingLeaveRequests();
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);
    }

    // Get a leave request to see if approved or rejected
    @GetMapping("/{id}")
    public ResponseEntity<LeaveRequestDto> getLeaveRequest(@PathVariable Long id) {
        LeaveRequestDto leaveRequest = leaveRequestService.getLeaveRequestById(id);
        if (leaveRequest == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(leaveRequest);
    }

    // Update (edit). This is for the admin/manager to approve or reject a leave
    // request.
    @PutMapping("/verdict")
    public ResponseEntity<String> updateLeaveRequest(
            @RequestParam("id") Long id,
            @RequestParam("status") String status,
            @RequestParam("comment") String comment) {

        if (status != null && !status.isEmpty()) {
            // Call the service to approve or reject the leave request
            if (status.equalsIgnoreCase("APPROVED")) {

                leaveRequestService.approveLeaveRequest(id, comment);

            } else if (status.equalsIgnoreCase("REJECTED")) {

                leaveRequestService.rejectLeaveRequest(id, comment);

            }
            return ResponseEntity.ok("Leave request updated successfully");

        }
        return ResponseEntity.badRequest().body("Invalid status provided");
    }
}