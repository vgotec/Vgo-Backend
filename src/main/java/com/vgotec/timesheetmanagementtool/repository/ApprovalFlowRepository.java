package com.vgotec.timesheetmanagementtool.repository;

import com.vgotec.timesheetmanagementtool.entity.ApprovalFlow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ApprovalFlowRepository extends JpaRepository<ApprovalFlow, UUID> {

    Optional<ApprovalFlow> findByName(String name);

    List<ApprovalFlow> findByEntityType(String entityType);

    List<ApprovalFlow> findByIsActiveTrue();
}
