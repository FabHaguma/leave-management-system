package com.ist.leavemanagementsystem.dto;

public class LeaveBalanceDto {
    private Long id;
    private Long userId;
    private Integer year;
    private Integer totalEntitlement;
    private Integer daysUsed;

    public LeaveBalanceDto() {}

    public LeaveBalanceDto(Long id, Long userId, Integer year, Integer totalEntitlement, Integer daysUsed) {
        this.id = id;
        this.userId = userId;
        this.year = year;
        this.totalEntitlement = totalEntitlement;
        this.daysUsed = daysUsed;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }

    public Integer getTotalEntitlement() { return totalEntitlement; }
    public void setTotalEntitlement(Integer totalEntitlement) { this.totalEntitlement = totalEntitlement; }

    public Integer getDaysUsed() { return daysUsed; }
    public void setDaysUsed(Integer daysUsed) { this.daysUsed = daysUsed; }
}
