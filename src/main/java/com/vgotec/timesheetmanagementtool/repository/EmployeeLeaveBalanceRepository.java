package com.vgotec.timesheetmanagementtool.repository;

import com.vgotec.timesheetmanagementtool.entity.EmployeeLeaveBalance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeLeaveBalanceRepository extends JpaRepository<EmployeeLeaveBalance, UUID> {

    List<EmployeeLeaveBalance> findByEmployeeIdAndYear(UUID employeeId, int year);

    Optional<EmployeeLeaveBalance> findByEmployeeIdAndLeaveTypeIdAndYear(UUID employeeId, UUID leaveTypeId, int year);
}
