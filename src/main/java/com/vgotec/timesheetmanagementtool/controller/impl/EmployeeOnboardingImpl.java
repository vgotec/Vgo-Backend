package com.vgotec.timesheetmanagementtool.controller.impl;

import com.vgotec.timesheetmanagementtool.controller.interfaces.EmployeeOnboarding;
import com.vgotec.timesheetmanagementtool.dto.EmployeeOnboarding.EmployeeOnboardingRequest;
import com.vgotec.timesheetmanagementtool.dto.EmployeeOnboarding.EmployeeOnboardingResponse;
import com.vgotec.timesheetmanagementtool.dto.EmployeeOnboarding.EmployeeUpdateRequest;
import com.vgotec.timesheetmanagementtool.dto.EmployeeOnboarding.EmployeeDto;
import com.vgotec.timesheetmanagementtool.entity.Employee;
import com.vgotec.timesheetmanagementtool.repository.EmployeeRepository;
import com.vgotec.timesheetmanagementtool.service.interfaces.EmployeeOnboardingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class EmployeeOnboardingImpl implements EmployeeOnboarding {

    private final EmployeeOnboardingService onboardingService;
    private final EmployeeRepository employeeRepo;

    public EmployeeOnboardingImpl(EmployeeOnboardingService onboardingService,
            EmployeeRepository employeeRepo) {
        this.onboardingService = onboardingService;
        this.employeeRepo = employeeRepo;
    }

    @Override
    public ResponseEntity<EmployeeDto> onboardEmployee(EmployeeOnboardingRequest request) {

        UUID employeeId = onboardingService.onboardEmployee(request);

        Employee saved = employeeRepo.findById(employeeId).orElse(null);

        EmployeeDto employeeDto = null;
        if (saved != null) {
            String fullName = saved.getPersonal().getFirstName() + " " + saved.getPersonal().getLastName();

            employeeDto = new EmployeeDto(
                    saved.getId(),
                    saved.getEmployeeCode(),
                    fullName,
                    saved.getRole(),
                    saved.getDesignation());
        }

        return ResponseEntity.ok(employeeDto);
    }

    @PutMapping("/employee/{id}")
    public ResponseEntity<?> updateEmployee(
            @PathVariable UUID id,
            @RequestBody EmployeeUpdateRequest req) {

        UUID updatedId = onboardingService.updateEmployee(id, req);

        return ResponseEntity.ok("Employee updated successfully: " + updatedId);
    }

    @DeleteMapping("/employee/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable UUID id) {

        onboardingService.deleteEmployee(id);

        return ResponseEntity.ok(
                "Employee deleted successfully: " + id);
    }

}
