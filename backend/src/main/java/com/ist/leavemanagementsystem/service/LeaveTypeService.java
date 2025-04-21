package com.ist.leavemanagementsystem.service;

import com.ist.leavemanagementsystem.dto.LeaveTypeDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LeaveTypeService {
    public List<LeaveTypeDto> getAllLeaveTypes() {
        // Dummy data for now; replace with repository/database logic as needed
        List<LeaveTypeDto> leaveTypes = new ArrayList<>();
        leaveTypes.add(new LeaveTypeDto(1L, "Annual Leave", 20));
        leaveTypes.add(new LeaveTypeDto(2L, "Sick Leave", 10));
        leaveTypes.add(new LeaveTypeDto(3L, "Maternity Leave", 90));
        return leaveTypes;
    }
}
