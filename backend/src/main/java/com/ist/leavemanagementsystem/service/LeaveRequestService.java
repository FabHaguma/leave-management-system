package com.ist.leavemanagementsystem.service;

import com.ist.leavemanagementsystem.dto.LeaveRequestDto;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface LeaveRequestService {
    LeaveRequestDto submitLeaveRequest(LeaveRequestDto leaveRequestDto, MultipartFile[] supportingDocuments);

    List<LeaveRequestDto> getUserLeaveRequests(Long userId);

    List<LeaveRequestDto> getPendingLeaveRequests();

    LeaveRequestDto checkLeaveApplicationStatus(Long userId);

    LeaveRequestDto getLeaveRequestById(Long id);

    LeaveRequestDto updateLeaveRequest(Long id, LeaveRequestDto leaveRequestDto);

    void deleteLeaveRequest(Long id);

    LeaveRequestDto approveLeaveRequest(Long requestId, String approverComment);

    LeaveRequestDto rejectLeaveRequest(Long requestId, String approverComment);
}