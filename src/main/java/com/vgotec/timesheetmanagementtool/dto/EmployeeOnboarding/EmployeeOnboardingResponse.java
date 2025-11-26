package com.vgotec.timesheetmanagementtool.dto.EmployeeOnboarding;

import java.util.UUID;

public class EmployeeOnboardingResponse {

    private String message;
    private UUID employeeId;
    private EmployeeDto employee;

    public EmployeeOnboardingResponse(String message, UUID employeeId, EmployeeDto employee) {
        this.message = message;
        this.employeeId = employeeId;
        this.employee = employee;
    }

    public String getMessage() {
        return message;
    }

    public UUID getEmployeeId() {
        return employeeId;
    }

    public EmployeeDto getEmployee() {
        return employee;
    }
}
