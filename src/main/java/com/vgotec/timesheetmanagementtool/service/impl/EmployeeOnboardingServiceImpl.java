package com.vgotec.timesheetmanagementtool.service.impl;

import com.vgotec.timesheetmanagementtool.dto.EmployeeOnboarding.EmployeeEducationDto;
import com.vgotec.timesheetmanagementtool.dto.EmployeeOnboarding.EmployeeOnboardingRequest;
import com.vgotec.timesheetmanagementtool.dto.EmployeeOnboarding.EmployeeUpdateRequest;
import com.vgotec.timesheetmanagementtool.entity.*;
import com.vgotec.timesheetmanagementtool.mapper.EmployeeEducationMapper;
import com.vgotec.timesheetmanagementtool.mapper.EmployeeMapper;
import com.vgotec.timesheetmanagementtool.mapper.EmployeePersonalMapper;
import com.vgotec.timesheetmanagementtool.mapper.EmployeeProfessionalMapper;
import com.vgotec.timesheetmanagementtool.repository.EmployeeRepository;
import com.vgotec.timesheetmanagementtool.service.interfaces.EmployeeOnboardingService;
import com.vgotec.timesheetmanagementtool.validation.EmployeeValidation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.List;

@Service
@Transactional
public class EmployeeOnboardingServiceImpl implements EmployeeOnboardingService {

    private final EmployeeRepository employeeRepo;
    private final EmployeeValidation validator;

    private final EmployeeMapper employeeMapper;
    private final EmployeePersonalMapper personalMapper;
    private final EmployeeProfessionalMapper professionalMapper;
    private final EmployeeEducationMapper educationMapper;

    public EmployeeOnboardingServiceImpl(
            EmployeeRepository employeeRepo,
            EmployeeValidation validator,
            EmployeeMapper employeeMapper,
            EmployeePersonalMapper personalMapper,
            EmployeeProfessionalMapper professionalMapper,
            EmployeeEducationMapper educationMapper) {

        this.employeeRepo = employeeRepo;
        this.validator = validator;
        this.employeeMapper = employeeMapper;
        this.personalMapper = personalMapper;
        this.professionalMapper = professionalMapper;
        this.educationMapper = educationMapper;
    }

    @Override
    public UUID onboardEmployee(EmployeeOnboardingRequest req) {

        // 1. Validate full request
        validator.validate(req);

        // 2. Convert DTO → Employee entity (basic fields)
        Employee emp = employeeMapper.toEntity(req);
        emp.setStatus("ACTIVE");
        emp.setRole(req.getRole()); // 👈 role directly stored in Employee as per your requirement

        // 3. reportingTo (convert UUID safely without helper class)
        if (req.getReportingToId() != null && !req.getReportingToId().isEmpty()) {
            try {
                UUID reportingToEmployeeId = UUID.fromString(req.getReportingToId());

                employeeRepo.findById(reportingToEmployeeId)
                        .ifPresent(emp::setReportingTo);

            } catch (IllegalArgumentException ex) {
                throw new IllegalArgumentException(
                        "Invalid reportingToId UUID: " + req.getReportingToId());
            }
        }

        // 4. Personal details
        EmployeePersonal personal = personalMapper.toEntity(req.getPersonal());
        emp.setPersonal(personal);

        // 5. Professional details
        EmployeeProfessional professional = professionalMapper.toEntity(req.getProfessional());
        emp.setProfessional(professional);

        // 6. Education details (list)
        req.getEducationList().forEach(eduDto -> {
            EmployeeEducation edu = educationMapper.toEntity(eduDto);
            emp.addEducation(edu); // automatically sets FK
        });

        // 7. Save (cascade will save personal, professional, education)
        employeeRepo.save(emp);

        // 8. Return Employee ID
        return emp.getId();
    }

    @Override
    public UUID updateEmployee(UUID employeeId, EmployeeUpdateRequest req) {

        // Load employee
        Employee emp = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // Basic details
        employeeMapper.updateEntity(emp, req);

        // Reporting To
        if (req.getReportingToId() != null && !req.getReportingToId().isEmpty()) {
            try {
                UUID reportingToId = UUID.fromString(req.getReportingToId());
                employeeRepo.findById(reportingToId)
                        .ifPresent(emp::setReportingTo);
            } catch (IllegalArgumentException ex) {
                throw new IllegalArgumentException("Invalid reportingToId UUID");
            }
        }

        // Personal
        if (req.getPersonal() != null) {
            personalMapper.updateEntity(emp.getPersonal(), req.getPersonal());
        }

        // Professional
        if (req.getProfessional() != null) {
            professionalMapper.updateEntity(emp.getProfessional(), req.getProfessional());
        }

        // Education List (STEP 4)
        updateEducationList(emp, req.getEducationList());

        // Save updated employee
        employeeRepo.save(emp);

        return emp.getId();
    }

    private void updateEducationList(Employee emp, List<EmployeeEducationDto> newEducationList) {

        if (newEducationList == null)
            return;

        // Remove all existing education records
        emp.getEducationList().clear();

        // Insert updated list
        newEducationList.forEach(dto -> {
            EmployeeEducation edu = educationMapper.toEntity(dto);
            emp.addEducation(edu);
        });
    }

    @Override
    public UUID deleteEmployee(UUID employeeId) {

        Employee emp = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        employeeRepo.delete(emp);

        return employeeId;
    }

}
