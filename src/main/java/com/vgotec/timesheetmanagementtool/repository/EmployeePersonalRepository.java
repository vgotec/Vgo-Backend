package com.vgotec.timesheetmanagementtool.repository;

import com.vgotec.timesheetmanagementtool.entity.EmployeePersonal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmployeePersonalRepository extends JpaRepository<EmployeePersonal, UUID> {
}
