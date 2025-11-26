package com.vgotec.timesheetmanagementtool.model;

import com.vgotec.timesheetmanagementtool.entity.User;
import java.util.UUID;

/**
 * Lightweight DTO for transferring User data (safe for API responses).
 */
public class UserModel {

    private UUID id;
    private String username;
    private String displayName;
    private String email;
    private String userType; // ADMIN, MANAGER, EMPLOYEE, etc.

    // -----------------------------
    // Constructors
    // -----------------------------
    public UserModel() {
    }

    public UserModel(UUID id, String username, String displayName, String email, String userType) {
        this.id = id;
        this.username = username;
        this.displayName = displayName;
        this.email = email;
        this.userType = userType;
    }

    // -----------------------------
    // Getters and Setters
    // -----------------------------
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    // -----------------------------
    // Utility Mapper
    // -----------------------------
    public static UserModel fromEntity(User user) {
        if (user == null) {
            return null;
        }

        return new UserModel(
                user.getId(),
                user.getUsername(),
                user.getDisplayName(),
                user.getEmail(),
                user.getUserType());
    }
}
