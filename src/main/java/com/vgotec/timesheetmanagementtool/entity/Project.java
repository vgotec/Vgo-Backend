package com.vgotec.timesheetmanagementtool.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid DEFAULT gen_random_uuid()")
    private UUID id;

    @Column(name = "project_code", unique = true, length = 50)
    private String projectCode;

    @Column(name = "project_name", nullable = false, length = 255)
    private String projectName;

    @Column(name = "project_cost", precision = 14)
    private Double projectCost;

    @Column(name = "client_name", length = 255)
    private String clientName;

    // 🔗 Reference to Employee for project head
    @Column(name = "project_head")
    private UUID projectHead;

    // 🔗 Reference to Employee for project manager
    @Column(name = "project_manager")
    private UUID projectManager;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "created_at")
    private OffsetDateTime createdAt = OffsetDateTime.now();

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt = OffsetDateTime.now();

    // ---------------- Constructors ----------------
    public Project() {
    }

    public Project(String projectCode, String projectName, String clientName) {
        this.projectCode = projectCode;
        this.projectName = projectName;
        this.clientName = clientName;
    }

    // ---------------- Lifecycle Hooks ----------------
    @PrePersist
    public void onCreate() {
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = OffsetDateTime.now();
    }

    // ---------------- Getters & Setters ----------------
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Double getProjectCost() {
        return projectCost;
    }

    public void setProjectCost(Double projectCost) {
        this.projectCost = projectCost;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public UUID getProjectHead() {
        return projectHead;
    }

    public void setProjectHead(UUID projectHead) {
        this.projectHead = projectHead;
    }

    public UUID getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(UUID projectManager) {
        this.projectManager = projectManager;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
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
