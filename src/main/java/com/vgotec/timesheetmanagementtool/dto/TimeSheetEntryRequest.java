package com.vgotec.timesheetmanagementtool.dto;

import java.time.LocalTime;
import java.util.UUID;

/**
 * DTO for creating or updating a single timesheet entry.
 */
public class TimeSheetEntryRequest {

    private UUID projectId;
    private UUID activityTypeId;
    private String activityDescription;
    private Double durationHours;
    private LocalTime startTime;
    private LocalTime endTime;

    // ---------- Constructors ----------
    public TimeSheetEntryRequest() {
    }

    public TimeSheetEntryRequest(UUID projectId, UUID activityTypeId, String activityDescription,
            Double durationHours, LocalTime startTime, LocalTime endTime) {
        this.projectId = projectId;
        this.activityTypeId = activityTypeId;
        this.activityDescription = activityDescription;
        this.durationHours = durationHours;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // ---------- Getters and Setters ----------

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

    // ---------- toString (Optional for Logging) ----------

}
