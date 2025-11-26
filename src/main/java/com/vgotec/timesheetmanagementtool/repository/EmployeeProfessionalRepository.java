package com.vgotec.timesheetmanagementtool.repository;

import com.vgotec.timesheetmanagementtool.entity.EmployeeProfessional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmployeeProfessionalRepository extends JpaRepository<EmployeeProfessional, UUID> {
}
