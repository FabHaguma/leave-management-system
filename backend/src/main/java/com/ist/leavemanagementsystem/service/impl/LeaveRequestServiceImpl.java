package com.ist.leavemanagementsystem.service.impl;

import com.ist.leavemanagementsystem.dto.LeaveRequestDto;
import com.ist.leavemanagementsystem.service.LeaveRequestService;
import com.ist.leavemanagementsystem.service.LeaveBalanceService;
import com.ist.leavemanagementsystem.service.NotificationService;
import com.ist.leavemanagementsystem.model.LeaveRequest;
import com.ist.leavemanagementsystem.model.LeaveType;
import com.ist.leavemanagementsystem.model.LeaveBalance;
import com.ist.leavemanagementsystem.model.User;
import com.ist.leavemanagementsystem.repository.LeaveRequestRepository;
import com.ist.leavemanagementsystem.repository.LeaveTypeRepository;
import com.ist.leavemanagementsystem.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import com.ist.leavemanagementsystem.model.Document;
import com.ist.leavemanagementsystem.repository.DocumentRepository;
import com.ist.leavemanagementsystem.repository.LeaveBalanceRepository;
import com.ist.leavemanagementsystem.util.MapperUtil;

import java.util.Collections;
import java.util.List;

@Service
public class LeaveRequestServiceImpl implements LeaveRequestService {
    @Autowired
    private LeaveBalanceService leaveBalanceService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private LeaveRequestRepository leaveRequestRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LeaveTypeRepository leaveTypeRepository;
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private LeaveBalanceRepository leaveBalanceRepository;
    @Autowired
    private MapperUtil mapperUtil;

    @Override
    public LeaveRequestDto submitLeaveRequest(LeaveRequestDto dto, MultipartFile[] supportingDocuments) {
        // 1. Create leave request
        LeaveRequest leaveRequest = mapperUtil.mapToLeaveRequest(dto);
        // 2. Check leave entitlement
        boolean hasEntitlement = leaveBalanceService.hasSufficientBalance(dto.getUserId(), dto.getLeaveTypeId(),
                leaveRequest.getDaysRequested());
        if (!hasEntitlement) {
            throw new IllegalArgumentException("Insufficient leave entitlement");
        }

        // 3. Save leave request as "PENDING"
        leaveRequest.setStatus("PENDING");
        leaveRequest = leaveRequestRepository.save(leaveRequest);

        if (supportingDocuments != null) {
            for (MultipartFile file : supportingDocuments) {
                // Save each document associated with the leave request
                Document document = new Document();
                document.setFileName(file.getOriginalFilename());
                // document.setFileUrl("/path/to/file"); // Replace with actual file path
                document.setLeaveRequestId(leaveRequest.getId());
                documentRepository.save(document);
            }
        }

        // 4. Notify manager
        notificationService.notifyPendingApproval(leaveRequest);

        // 5. Return the saved leave request as a DTO
        return dto;
    }

    @Override
    public LeaveRequestDto approveLeaveRequest(Long requestId, String approverComment) {
        // 1. Mark leave request as APPROVED
        LeaveRequest leaveRequest = leaveRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Leave request not found with ID: " + requestId));
        leaveRequest.setStatus("APPROVED");
        leaveRequest.setComment(approverComment);
        leaveRequestRepository.save(leaveRequest);

        int daysRequested = (int) (leaveRequest.getEndDate().toEpochDay() - leaveRequest.getStartDate().toEpochDay())
                + 1; // +1 to include both start and end dates

        // 2. Update leave balance
        LeaveBalance leaveBalance = leaveBalanceRepository.findByUserIdAndLeaveTypeId(leaveRequest.getUserId(),
                leaveRequest.getLeaveTypeId())
                .orElseThrow(() -> new IllegalArgumentException("Leave balance not found for user ID: "
                        + leaveRequest.getUserId() + " and leave type ID: " + leaveRequest.getLeaveTypeId()));
        LeaveType leaveType = leaveTypeRepository.findById(leaveRequest.getLeaveTypeId())
                .orElseThrow(() -> new IllegalArgumentException("Leave type not found with ID: "
                        + leaveRequest.getLeaveTypeId()));

        leaveBalance.setUsed(leaveBalance.getUsed() + daysRequested);
        leaveBalance.setRemaining(leaveType.getDefaultDays() + leaveBalance.getEntitlement()
                - leaveBalance.getUsed());
        leaveBalanceRepository.save(leaveBalance);

        // 3. Notify user about approval
        User user = userRepository.findById(leaveRequest.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + leaveRequest.getUserId()));
        notificationService.notifyLeaveApproved(user, leaveRequest);

        // 4. Convert to DTO and return
        return mapperUtil.mapToLeaveRequestDto(leaveRequest, user.getName());
    }

    @Override
    public LeaveRequestDto rejectLeaveRequest(Long requestId, String approverComment) {
        // 1. Mark leave request as REJECTED
        LeaveRequest leaveRequest = leaveRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Leave request not found with ID: " + requestId));
        LeaveType leaveType = leaveTypeRepository.findById(leaveRequest.getLeaveTypeId())
                .orElseThrow(() -> new IllegalArgumentException("Leave type not found with ID: "
                        + leaveRequest.getLeaveTypeId()));

        leaveRequest.setStatus("REJECTED");
        leaveRequest.setComment(approverComment);
        leaveRequestRepository.save(leaveRequest);

        // 2. Notify user about rejection
        User user = userRepository.findById(leaveRequest.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + leaveRequest.getUserId()));
        notificationService.notifyLeaveRejected(user, leaveRequest);

        // 3. Convert to DTO and return
        return mapperUtil.mapToLeaveRequestDto(leaveRequest, user.getName());
    }

    @Override
    public List<LeaveRequestDto> getUserLeaveRequests(Long userId) {
        List<LeaveRequest> leaveRequests = leaveRequestRepository.findByUserIdAndStatus(userId, "APPROVED");
        if (leaveRequests.isEmpty()) {
            return Collections.emptyList();
        } else {
            return leaveRequests.stream()
                    .map(leaveRequest -> mapperUtil.mapToLeaveRequestDto(leaveRequest, null))
                    .toList();
        }
    }

    @Override
    public java.util.List<LeaveRequestDto> getPendingLeaveRequests() {
        List<LeaveRequest> leaveRequests = leaveRequestRepository.findByStatus("PENDING");
        if (leaveRequests.isEmpty()) {
            return Collections.emptyList();
        } else {
            return leaveRequests.stream().map(leaveRequest -> {
                LeaveRequestDto dto = new LeaveRequestDto();
                dto.setId(leaveRequest.getId());
                dto.setUserId(leaveRequest.getUserId());
                dto.setLeaveTypeId(leaveRequest.getLeaveTypeId());
                dto.setStartDate(leaveRequest.getStartDate());
                dto.setEndDate(leaveRequest.getEndDate());
                dto.setReason(leaveRequest.getReason());
                dto.setStatus(leaveRequest.getStatus());
                dto.setComment(leaveRequest.getComment());
                return dto;
            }).toList();
        }
    }

    @Override
    public LeaveRequestDto getLeaveRequestById(Long id) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Leave request not found with ID: " + id));
        LeaveRequestDto dto = new LeaveRequestDto();
        dto.setId(leaveRequest.getId());
        dto.setUserId(leaveRequest.getUserId());
        dto.setLeaveTypeId(leaveRequest.getLeaveTypeId());
        dto.setStartDate(leaveRequest.getStartDate());
        dto.setEndDate(leaveRequest.getEndDate());
        dto.setReason(leaveRequest.getReason());
        dto.setStatus(leaveRequest.getStatus());
        dto.setComment(leaveRequest.getComment());
        return dto;
    }

    @Override
    public LeaveRequestDto updateLeaveRequest(Long id, LeaveRequestDto leaveRequestDto) {
        // TODO: Implement logic to update a leave request
        return null;
    }

    @Override
    public void deleteLeaveRequest(Long id) {
        // TODO: Implement logic to delete a leave request
    }

    @Override
    public LeaveRequestDto checkLeaveApplicationStatus(Long userId) {
        LeaveRequest leaveRequest = leaveRequestRepository.findTopByUserIdOrderByCreatedAtDesc(userId);
        if (leaveRequest == null) {
            return null;
        }
        LeaveRequestDto dto = new LeaveRequestDto();
        dto.setId(leaveRequest.getId());
        dto.setUserId(leaveRequest.getUserId());
        dto.setLeaveTypeId(leaveRequest.getLeaveTypeId());
        dto.setStartDate(leaveRequest.getStartDate());
        dto.setEndDate(leaveRequest.getEndDate());
        dto.setReason(leaveRequest.getReason());
        dto.setStatus(leaveRequest.getStatus());
        dto.setComment(leaveRequest.getComment());
        return dto;
    }

    private String getLeaveTypeNameById(long leaveTypeId, List<LeaveType> leaveTypes) {
        for (LeaveType leaveType : leaveTypes) {
            if (leaveType.getId() == leaveTypeId) {
                return leaveType.getName();
            }
        }
        return null;
    }

}