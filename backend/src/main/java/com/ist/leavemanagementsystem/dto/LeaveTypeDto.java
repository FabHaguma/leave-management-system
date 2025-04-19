package com.ist.leavemanagementsystem.dto;

public class LeaveTypeDto {
    private Long id;
    private String name;
    private String description;
    private Integer defaultDays;

    public LeaveTypeDto() {}

    public LeaveTypeDto(Long id, String name, String description, Integer defaultDays) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.defaultDays = defaultDays;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getDefaultDays() { return defaultDays; }
    public void setDefaultDays(Integer defaultDays) { this.defaultDays = defaultDays; }
}
