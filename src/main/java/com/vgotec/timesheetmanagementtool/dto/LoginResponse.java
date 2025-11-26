package com.vgotec.timesheetmanagementtool.dto;

import java.util.UUID;

public class LoginResponse {
    private UUID userId;
    private String name;
    private String userType; // Changed from Role enum to String
    private String avatarUrl;

    // Constructors
    public LoginResponse() {
    }

    public LoginResponse(UUID userId, String name, String userType, String avatarUrl) {
        this.userId = userId;
        this.name = name;
        this.userType = userType;
        this.avatarUrl = avatarUrl;
    }

    // Getters & Setters
    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
