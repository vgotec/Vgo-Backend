package com.vgotec.timesheetmanagementtool.repository;

import com.vgotec.timesheetmanagementtool.entity.LeaveStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface LeaveStatusRepository extends JpaRepository<LeaveStatusEntity, UUID> {
    Optional<LeaveStatusEntity> findByNameIgnoreCase(String name);
}
