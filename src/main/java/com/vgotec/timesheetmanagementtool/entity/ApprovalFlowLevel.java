package com.vgotec.timesheetmanagementtool.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
//import java.util.HashMap;
//import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "approval_flow_level", uniqueConstraints = {
        @UniqueConstraint(name = "uniq_flow_level", columnNames = { "approval_flow_id", "level_number" })
}, indexes = {
        @Index(name = "idx_flow_level_flow", columnList = "approval_flow_id")
})
public class ApprovalFlowLevel {

    /** Primary key — UUID */
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "uuid DEFAULT gen_random_uuid()", updatable = false, nullable = false)
    private UUID id;

    /** FK → approval_flow(id) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approval_flow_id", nullable = false, foreignKey = @ForeignKey(name = "approval_flow_level_approval_flow_id_fkey"))
    private ApprovalFlow approvalFlow;

    /** Level number (1, 2, 3, ...) */
    @Column(name = "level_number", nullable = false)
    private Integer levelNumber;

    /** Approver user (if user-specific) */
    @Column(name = "approver_user_id")
    private UUID approverUserId;

    /** Approver role (if role-based approval) */
    @Column(name = "approver_role", length = 100)
    private String approverRole;

    /** Max pending hours before escalation */
    @Column(name = "max_pending_hours", precision = 10, scale = 2)
    private BigDecimal maxPendingHours;

    /** If this approval is mandatory */
    @Column(name = "must_approve", nullable = false)
    private boolean mustApprove = true;

    /** Timestamp */
    @Column(name = "created_at")
    private OffsetDateTime createdAt = OffsetDateTime.now();

    // ---------- Lifecycle Hooks ----------
    @PrePersist
    public void onCreate() {
        this.createdAt = OffsetDateTime.now();
    }

    // ---------- Getters & Setters ----------
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ApprovalFlow getApprovalFlow() {
        return approvalFlow;
    }

    public void setApprovalFlow(ApprovalFlow approvalFlow) {
        this.approvalFlow = approvalFlow;
    }

    public Integer getLevelNumber() {
        return levelNumber;
    }

    public void setLevelNumber(Integer levelNumber) {
        this.levelNumber = levelNumber;
    }

    public UUID getApproverUserId() {
        return approverUserId;
    }

    public void setApproverUserId(UUID approverUserId) {
        this.approverUserId = approverUserId;
    }

    public String getApproverRole() {
        return approverRole;
    }

    public void setApproverRole(String approverRole) {
        this.approverRole = approverRole;
    }

    public BigDecimal getMaxPendingHours() {
        return maxPendingHours;
    }

    public void setMaxPendingHours(BigDecimal maxPendingHours) {
        this.maxPendingHours = maxPendingHours;
    }

    public boolean isMustApprove() {
        return mustApprove;
    }

    public void setMustApprove(boolean mustApprove) {
        this.mustApprove = mustApprove;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // public Map<String, Object> getMetadata() {
    // return metadata;
    // }

    // public void setMetadata(Map<String, Object> metadata) {
    // this.metadata = metadata;
    // }
}
