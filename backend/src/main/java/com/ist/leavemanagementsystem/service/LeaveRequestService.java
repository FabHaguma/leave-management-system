package com.ist.leavemanagementsystem.service;

import com.ist.leavemanagementsystem.dto.LeaveRequestDto;
import org.springframework.security.oauth2.core.user.OAuth2User;
import java.util.List;

public interface LeaveRequestService {
    LeaveRequestDto submitLeaveRequest(LeaveRequestDto leaveRequestDto, OAuth2User principal);
    List<LeaveRequestDto> getUserLeaveRequests(OAuth2User principal);
    List<LeaveRequestDto> getPendingLeaveRequests();
    LeaveRequestDto getLeaveRequestById(Long id);
    LeaveRequestDto updateLeaveRequest(Long id, LeaveRequestDto leaveRequestDto);
    void deleteLeaveRequest(Long id);
    LeaveRequestDto approveLeaveRequest(Long requestId, String approverComment);
    LeaveRequestDto rejectLeaveRequest(Long requestId, String approverComment);
}