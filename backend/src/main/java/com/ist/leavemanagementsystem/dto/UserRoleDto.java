package com.ist.leavemanagementsystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRoleDto {
    // private Long id;
    private Long userId;
    private Long roleId;
    private String roleName;
}
