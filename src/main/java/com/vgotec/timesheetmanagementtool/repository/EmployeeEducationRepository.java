package com.vgotec.timesheetmanagementtool.repository;

import com.vgotec.timesheetmanagementtool.entity.EmployeeEducation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmployeeEducationRepository extends JpaRepository<EmployeeEducation, UUID> {
}
