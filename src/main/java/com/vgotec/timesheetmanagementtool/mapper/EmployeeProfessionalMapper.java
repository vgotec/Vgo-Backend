package com.vgotec.timesheetmanagementtool.mapper;

import com.vgotec.timesheetmanagementtool.dto.EmployeeOnboarding.EmployeeProfessionalDto;
import com.vgotec.timesheetmanagementtool.entity.EmployeeProfessional;
import org.springframework.stereotype.Component;

@Component
public class EmployeeProfessionalMapper {

    public EmployeeProfessional toEntity(EmployeeProfessionalDto dto) {
        if (dto == null)
            return null;

        EmployeeProfessional pro = new EmployeeProfessional();
        pro.setExperienceYears(dto.getExperienceYears());
        pro.setLocation(dto.getLocation());
        pro.setSkills(dto.getSkills());
        pro.setPreviousCompanyName(dto.getPreviousCompanyName());
        pro.setPreviousCompanyAddress(dto.getPreviousCompanyAddress());
        pro.setPreviousDesignation(dto.getPreviousDesignation());
        return pro;
    }

    public void updateEntity(EmployeeProfessional pro, EmployeeProfessionalDto dto) {
        if (dto == null)
            return;

        if (dto.getExperienceYears() != null)
            pro.setExperienceYears(dto.getExperienceYears());
        if (dto.getLocation() != null)
            pro.setLocation(dto.getLocation());
        if (dto.getSkills() != null)
            pro.setSkills(dto.getSkills());
        if (dto.getPreviousCompanyName() != null)
            pro.setPreviousCompanyName(dto.getPreviousCompanyName());
        if (dto.getPreviousCompanyAddress() != null)
            pro.setPreviousCompanyAddress(dto.getPreviousCompanyAddress());
        if (dto.getPreviousDesignation() != null)
            pro.setPreviousDesignation(dto.getPreviousDesignation());
    }

}
