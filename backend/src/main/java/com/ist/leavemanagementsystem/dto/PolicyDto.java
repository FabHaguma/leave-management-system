package com.ist.leavemanagementsystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PolicyDto {
    private Long id;
    private String name;
    private Integer annualEntitlement;
    private int accrualRatePerMonth;
    private Integer maxCarryForwardDays;
    private Boolean allowNegativeBalance;
    private String notes;
}
