package com.ist.leavemanagementsystem.dto;

public class PolicyDto {
    private Long id;
    private String name;
    private Integer annualEntitlement;
    private Double accrualRatePerMonth;
    private Integer maxCarryForwardDays;
    private Boolean allowNegativeBalance;
    private String notes;

    public PolicyDto() {}

    public PolicyDto(Long id, String name, Integer annualEntitlement, Double accrualRatePerMonth, Integer maxCarryForwardDays, Boolean allowNegativeBalance, String notes) {
        this.id = id;
        this.name = name;
        this.annualEntitlement = annualEntitlement;
        this.accrualRatePerMonth = accrualRatePerMonth;
        this.maxCarryForwardDays = maxCarryForwardDays;
        this.allowNegativeBalance = allowNegativeBalance;
        this.notes = notes;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getAnnualEntitlement() { return annualEntitlement; }
    public void setAnnualEntitlement(Integer annualEntitlement) { this.annualEntitlement = annualEntitlement; }

    public Double getAccrualRatePerMonth() { return accrualRatePerMonth; }
    public void setAccrualRatePerMonth(Double accrualRatePerMonth) { this.accrualRatePerMonth = accrualRatePerMonth; }

    public Integer getMaxCarryForwardDays() { return maxCarryForwardDays; }
    public void setMaxCarryForwardDays(Integer maxCarryForwardDays) { this.maxCarryForwardDays = maxCarryForwardDays; }

    public Boolean getAllowNegativeBalance() { return allowNegativeBalance; }
    public void setAllowNegativeBalance(Boolean allowNegativeBalance) { this.allowNegativeBalance = allowNegativeBalance; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
