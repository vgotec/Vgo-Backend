package com.vgotec.timesheetmanagementtool.entity;

import jakarta.persistence.*;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "timesheet_entry", indexes = {
        @Index(name = "idx_timesheet_entry_timesheet_id", columnList = "timesheet_id"),
        @Index(name = "idx_timesheet_entry_project_id", columnList = "project_id")
})
public class TimesheetEntry {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid DEFAULT gen_random_uuid()", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "timesheet_id", nullable = false)
    private TimeSheet timesheet;

    @Column(name = "project_id")
    private UUID projectId;

    @Column(name = "activity_type_id")
    private UUID activityTypeId;

    @Column(name = "activity_description", length = 255)
    private String activityDescription;

    @Column(name = "duration_hours", precision = 5)
    private Double durationHours;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "created_at")
    private OffsetDateTime createdAt = OffsetDateTime.now();

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    // Default constructor
    public TimesheetEntry() {
    }

    // Optional convenience constructor
    public TimesheetEntry(TimeSheet timesheet, UUID projectId, UUID activityTypeId,
            String activityDescription, Double durationHours,
            LocalTime startTime, LocalTime endTime) {
        this.timesheet = timesheet;
        this.projectId = projectId;
        this.activityTypeId = activityTypeId;
        this.activityDescription = activityDescription;
        this.durationHours = durationHours;
        this.startTime = startTime;
        this.endTime = endTime;

    }

    @PrePersist
    public void onCreate() {
        this.createdAt = OffsetDateTime.now();
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

    public TimeSheet getTimesheet() {
        return timesheet;
    }

    public void setTimesheet(TimeSheet timesheet) {
        this.timesheet = timesheet;
    }

    public UUID getProjectId() {
        return projectId;
    }

    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
    }

    public UUID getActivityTypeId() {
        return activityTypeId;
    }

    public void setActivityTypeId(UUID activityTypeId) {
        this.activityTypeId = activityTypeId;
    }

    public String getActivityDescription() {
        return activityDescription;
    }

    public void setActivityDescription(String activityDescription) {
        this.activityDescription = activityDescription;
    }

    public Double getDurationHours() {
        return durationHours;
    }

    public void setDurationHours(Double durationHours) {
        this.durationHours = durationHours;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
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
