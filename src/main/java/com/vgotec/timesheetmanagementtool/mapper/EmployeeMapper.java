package com.vgotec.timesheetmanagementtool.mapper;

import com.vgotec.timesheetmanagementtool.dto.EmployeeOnboarding.EmployeeOnboardingRequest;
import com.vgotec.timesheetmanagementtool.dto.EmployeeOnboarding.EmployeeUpdateRequest;
import com.vgotec.timesheetmanagementtool.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public Employee toEntity(EmployeeOnboardingRequest dto) {
        if (dto == null)
            return null;

        Employee e = new Employee();
        e.setEmployeeCode(dto.getEmployeeCode());
        e.setEmail(dto.getEmail());
        e.setDesignation(dto.getDesignation());
        e.setDepartment(dto.getDepartment());
        e.setDivision(dto.getDivision());
        e.setJoiningDate(dto.getJoiningDate());

        e.setStatus("ACTIVE");
        return e;
    }

    public void updateEntity(Employee emp, EmployeeUpdateRequest dto) {

        if (dto.getEmail() != null)
            emp.setEmail(dto.getEmail());
        if (dto.getDesignation() != null)
            emp.setDesignation(dto.getDesignation());
        if (dto.getDepartment() != null)
            emp.setDepartment(dto.getDepartment());
        if (dto.getDivision() != null)
            emp.setDivision(dto.getDivision());
        if (dto.getJoiningDate() != null)
            emp.setJoiningDate(dto.getJoiningDate());
        if (dto.getRole() != null)
            emp.setRole(dto.getRole());
    }

}
