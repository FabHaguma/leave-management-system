package com.ist.leavemanagementsystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Added Lombok
@NoArgsConstructor // Added Lombok
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private String profilePicture;
    private String role;
}