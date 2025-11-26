package com.vgotec.timesheetmanagementtool.validation;

import com.vgotec.timesheetmanagementtool.dto.EmployeeOnboarding.EmployeeOnboardingRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class EmployeeValidation {

    public void validate(EmployeeOnboardingRequest req) {

        if (!StringUtils.hasText(req.getEmployeeCode()))
            throw new IllegalArgumentException("Employee code is required");

        if (!StringUtils.hasText(req.getEmail()))
            throw new IllegalArgumentException("Employee email is required");

        if (req.getJoiningDate() == null)
            throw new IllegalArgumentException("Joining date is required");

        if (req.getPersonal() == null)
            throw new IllegalArgumentException("Personal details are required");

        if (!StringUtils.hasText(req.getPersonal().getFirstName()))
            throw new IllegalArgumentException("First name is required");

        if (req.getProfessional() == null)
            throw new IllegalArgumentException("Professional details are required");

        if (req.getEducationList() == null || req.getEducationList().isEmpty())
            throw new IllegalArgumentException("At least one education record is required");

        if (!StringUtils.hasText(req.getRole()))
            throw new IllegalArgumentException("Role is required");
    }
}
