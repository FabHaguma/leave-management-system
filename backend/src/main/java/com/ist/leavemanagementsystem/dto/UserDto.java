package com.ist.leavemanagementsystem.dto;

public class UserDto {
    private String name;
    private String email;
    private String avatarUrl;
    private String role;

    public UserDto() {
    }

    public UserDto(String name, String email, String avatarUrl, String role) {
        this.name = name;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}