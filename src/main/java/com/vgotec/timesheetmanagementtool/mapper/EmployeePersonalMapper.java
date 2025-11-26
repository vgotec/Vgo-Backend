package com.vgotec.timesheetmanagementtool.mapper;

import com.vgotec.timesheetmanagementtool.dto.EmployeeOnboarding.EmployeePersonalDto;
import com.vgotec.timesheetmanagementtool.entity.EmployeePersonal;
import org.springframework.stereotype.Component;

@Component
public class EmployeePersonalMapper {

    public EmployeePersonal toEntity(EmployeePersonalDto dto) {
        if (dto == null)
            return null;

        EmployeePersonal p = new EmployeePersonal();
        p.setFirstName(dto.getFirstName());
        p.setLastName(dto.getLastName());
        p.setEmail(dto.getEmail());
        p.setDateOfBirth(dto.getDateOfBirth());
        p.setAadharNumber(dto.getAadharNumber());
        p.setPanNumber(dto.getPanNumber());
        p.setUanNumber(dto.getUanNumber());
        p.setPresentAddress(dto.getPresentAddress());
        p.setPermanentAddress(dto.getPermanentAddress());
        return p;
    }

    public void updateEntity(EmployeePersonal entity, EmployeePersonalDto dto) {
        if (dto == null)
            return;

        if (dto.getFirstName() != null)
            entity.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null)
            entity.setLastName(dto.getLastName());
        if (dto.getEmail() != null)
            entity.setEmail(dto.getEmail());
        if (dto.getDateOfBirth() != null)
            entity.setDateOfBirth(dto.getDateOfBirth());
        if (dto.getAadharNumber() != null)
            entity.setAadharNumber(dto.getAadharNumber());
        if (dto.getPanNumber() != null)
            entity.setPanNumber(dto.getPanNumber());
        if (dto.getUanNumber() != null)
            entity.setUanNumber(dto.getUanNumber());
        if (dto.getPresentAddress() != null)
            entity.setPresentAddress(dto.getPresentAddress());
        if (dto.getPermanentAddress() != null)
            entity.setPermanentAddress(dto.getPermanentAddress());
    }

}
