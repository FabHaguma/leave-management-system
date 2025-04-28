package com.ist.leavemanagementsystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data // Added Lombok
@NoArgsConstructor // Added Lombok
public class LeaveRequestDto {
    private Long id;
    private Long userId;
    private Long leaveTypeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
    private String comment;
    private String status; // e.g., "PENDING", "APPROVED", "REJECTED"
    private String leaveTypeName;
    private String userName;
    private String userEmail;
    private boolean hasDocuments;
}