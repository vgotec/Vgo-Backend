package com.vgotec.timesheetmanagementtool.dto;

import com.vgotec.timesheetmanagementtool.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO for creating new User (and optional Employee) entries.
 * Reflects fields from both User and Employee entities.
 */
public class SignupRequest {

    // ---------- User fields ----------
    @NotBlank(message = "Name is required")
    @Size(max = 200, message = "Name cannot exceed 200 characters")
    private String name; // maps to displayName in User

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String email; // maps to User.email

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 255, message = "Password must be between 6 and 255 characters")
    private String password; // maps to User.passwordHash

    private Role role; // will be converted to String in User.userType

    // ---------- Employee fields ----------
    @Size(max = 50, message = "Employee code cannot exceed 50 characters")
    private String employeeCode;

    @Size(max = 100, message = "Designation cannot exceed 100 characters")
    private String designation;

    @Size(max = 100, message = "Department cannot exceed 100 characters")
    private String department;

    @Size(max = 100, message = "Division cannot exceed 100 characters")
    private String division;

    private Boolean isActive = true;

    // ---------- Constructors ----------
    public SignupRequest() {
    }

    public SignupRequest(String name, String email, String password, Role role,
            String employeeCode, String designation, String department,
            String division, Boolean isActive) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.employeeCode = employeeCode;
        this.designation = designation;
        this.department = department;
        this.division = division;
        this.isActive = isActive;
    }

    // ---------- Getters & Setters ----------
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
