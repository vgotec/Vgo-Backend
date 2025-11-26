package com.vgotec.timesheetmanagementtool.controller.interfaces;

import com.vgotec.timesheetmanagementtool.dto.EmployeeOnboarding.EmployeeOnboardingRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/employees")
public interface EmployeeOnboarding {

    @PostMapping("/onboard")
    ResponseEntity<?> onboardEmployee(@RequestBody EmployeeOnboardingRequest request);
}
