package com.ist.leavemanagementsystem.service;

import com.ist.leavemanagementsystem.dto.LeaveTypeDto;
import com.ist.leavemanagementsystem.repository.LeaveTypeRepository;
import com.ist.leavemanagementsystem.util.MapperUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ist.leavemanagementsystem.model.LeaveType;

import java.util.List;

@Service
public class LeaveTypeService {

    @Autowired
    private LeaveTypeRepository leaveTypeRepository;
    @Autowired
    private MapperUtil mapperUtil;

    public List<LeaveTypeDto> getAllLeaveTypes() {
        // Fetch leave types from the database using the repository
        return leaveTypeRepository.findAll().stream()
                .map(leaveType -> mapperUtil.mapToLeaveTypeDto(leaveType))
                .toList();
    }

    public LeaveTypeDto createLeaveType(LeaveTypeDto leaveTypeDto) {
        // Convert DTO to entity and save to the database
        LeaveType leaveType = mapperUtil.mapToLeaveType(leaveTypeDto);
        LeaveType savedLeaveType = leaveTypeRepository.save(leaveType);

        // Convert saved entity back to DTO and return
        return mapperUtil.mapToLeaveTypeDto(savedLeaveType);
    }
}
