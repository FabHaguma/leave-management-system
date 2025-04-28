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
    private int entitlement; // Renamed from totalEntitlement
    private int used; // Renamed from daysUsed
    private int remaining; // Added remaining balance
    private String userName;
}
