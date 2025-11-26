package com.vgotec.timesheetmanagementtool.mapper;

import com.vgotec.timesheetmanagementtool.dto.EmployeeOnboarding.EmployeeEducationDto;
import com.vgotec.timesheetmanagementtool.entity.EmployeeEducation;
import org.springframework.stereotype.Component;

@Component
public class EmployeeEducationMapper {

    public EmployeeEducation toEntity(EmployeeEducationDto dto) {
        if (dto == null)
            return null;

        EmployeeEducation edu = new EmployeeEducation();
        edu.setHighestQualification(dto.getHighestQualification());
        edu.setInstituteName(dto.getInstituteName());
        edu.setDegreeDiploma(dto.getDegreeDiploma());
        edu.setCompletionDate(dto.getCompletionDate());
        return edu;
    }

}
