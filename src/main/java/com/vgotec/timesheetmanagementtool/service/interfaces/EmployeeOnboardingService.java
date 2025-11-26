package com.vgotec.timesheetmanagementtool.service.interfaces;

import com.vgotec.timesheetmanagementtool.dto.EmployeeOnboarding.EmployeeOnboardingRequest;
import com.vgotec.timesheetmanagementtool.dto.EmployeeOnboarding.EmployeeUpdateRequest;

import java.util.UUID;

public interface EmployeeOnboardingService {
    UUID onboardEmployee(EmployeeOnboardingRequest request);

    UUID updateEmployee(UUID employeeId, EmployeeUpdateRequest request);

    UUID deleteEmployee(UUID employeeId);
}
