package com.vgotec.timesheetmanagementtool.repository;

import com.vgotec.timesheetmanagementtool.entity.TimesheetStatusColor;
import com.vgotec.timesheetmanagementtool.enums.TimeSheetStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TimesheetStatusColorRepository extends JpaRepository<TimesheetStatusColor, UUID> {
    Optional<TimesheetStatusColor> findByStatus(TimeSheetStatus status);
}
