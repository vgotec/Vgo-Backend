package com.vgotec.timesheetmanagementtool.dto.EmployeeOnboarding;

public class EmployeeUpdateResponse {

    private String message;
    private EmployeeDetailDto employee;

    public EmployeeUpdateResponse(String message, EmployeeDetailDto employee) {
        this.message = message;
        this.employee = employee;
    }

    public String getMessage() {
        return message;
    }

    public EmployeeDetailDto getEmployee() {
        return employee;
    }
}
