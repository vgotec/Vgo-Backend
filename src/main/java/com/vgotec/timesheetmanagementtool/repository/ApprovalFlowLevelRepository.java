package com.vgotec.timesheetmanagementtool.repository;

import com.vgotec.timesheetmanagementtool.entity.ApprovalFlowLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ApprovalFlowLevelRepository extends JpaRepository<ApprovalFlowLevel, UUID> {

    List<ApprovalFlowLevel> findByApprovalFlowId(UUID approvalFlowId);

    List<ApprovalFlowLevel> findByApproverUserId(UUID approverUserId);

    boolean existsByApprovalFlowIdAndLevelNumber(UUID approvalFlowId, Integer levelNumber);
}
