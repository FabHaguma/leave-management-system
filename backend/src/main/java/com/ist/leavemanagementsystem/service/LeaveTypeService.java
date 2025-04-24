package com.ist.leavemanagementsystem.service;

import com.ist.leavemanagementsystem.dto.LeaveTypeDto;
import com.ist.leavemanagementsystem.repository.LeaveTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveTypeService {

    @Autowired
    private LeaveTypeRepository leaveTypeRepository;

    public List<LeaveTypeDto> getAllLeaveTypes() {
        // Fetch leave types from the database using the repository
        return leaveTypeRepository.findAll().stream()
                .map(leaveType -> new LeaveTypeDto(leaveType.getId(), leaveType.getName(), leaveType.getDefaultDays()))
                .toList();
    }
}
