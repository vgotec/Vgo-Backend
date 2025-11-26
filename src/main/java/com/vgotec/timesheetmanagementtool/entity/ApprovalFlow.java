package com.vgotec.timesheetmanagementtool.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "approval_flow", indexes = {
        @Index(name = "idx_approval_flow_entity", columnList = "entity_type")
})
public class ApprovalFlow {

    /** Primary key — UUID */
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "uuid DEFAULT gen_random_uuid()", updatable = false, nullable = false)
    private UUID id;

    /** Flow name (e.g., "Timesheet Approval", "Leave Approval") */
    @Column(nullable = false, length = 200)
    private String name;

    /** Description of the approval process */
    @Column(columnDefinition = "text")
    private String description;

    /** Entity type this flow applies to (e.g., TIMESHEET, LEAVE) */
    @Column(name = "entity_type", nullable = false, length = 50)
    private String entityType;

    /** Whether this approval flow is active */
    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    /** Creation timestamp */
    @Column(name = "created_at")
    private OffsetDateTime createdAt = OffsetDateTime.now();

    /** Update timestamp */
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt = OffsetDateTime.now();

    public void onCreate() {
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = OffsetDateTime.now();
    }

    // ---------- Getters and Setters ----------
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}