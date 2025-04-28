package com.ist.leavemanagementsystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor; // Add Lombok

@Data // Add Lombok
@NoArgsConstructor // Add Lombok
public class LeaveTypeDto {
    private Long id;
    private String name;
    private Integer defaultDays;
}
