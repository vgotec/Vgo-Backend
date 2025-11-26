package com.vgotec.timesheetmanagementtool.dto.EmployeeOnboarding;

import java.util.UUID;

public class EmployeeDto {

    private UUID employeeId;
    private String employeeCode;
    private String fullName;
    private String role;
    private String designation;

    public EmployeeDto(UUID employeeId, String employeeCode, String fullName, String role, String designation) {
        this.employeeId = employeeId;
        this.employeeCode = employeeCode;
        this.fullName = fullName;
        this.role = role;
        this.designation = designation;
    }

    public UUID getEmployeeId() {
        return employeeId;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public String getFullName() {
        return fullName;
    }

    public String getRole() {
        return role;
    }

    public String getDesignation() {
        return designation;
    }
}
