package com.ist.leavemanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaveBalanceDto {
    private Long id;
    private Long userId;
    private Long leaveTypeId; // Added leaveTypeId
    private String leaveTypeName; // Added leaveTypeName for display
    private double entitlement; // Renamed from totalEntitlement
    private double used; // Renamed from daysUsed
    private double remaining; // Added remaining balance
}
