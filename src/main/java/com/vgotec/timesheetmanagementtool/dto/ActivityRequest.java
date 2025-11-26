package com.vgotec.timesheetmanagementtool.dto;

import jakarta.validation.constraints.*;
import java.time.LocalTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * DTO for activity creation or update requests (frontend-friendly version).
 * 
 * This DTO bridges the frontend JSON request with backend entity lookups.
 * 
 * Frontend sends:
 * {
 * "project": "PRJ001 - Payroll System",
 * "activity": "Testing",
 * "category": "Quality Assurance",
 * "description": "Ran test cases for new release",
 * "workHours": 3.5,
 * "startTime": "10:00:00",
 * "endTime": "13:30:00"
 * }
 * 
 * Backend then resolves projectId and activityTypeId automatically.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActivityRequest {

    // 🔹 Resolved in backend after project lookup
    private UUID projectId;

    // 🔹 Resolved in backend after activityType lookup
    private UUID activityTypeId;

    @NotBlank(message = "Project is required")
    private String project; // e.g., "PRJ001 - Payroll System"

    @NotBlank(message = "Activity name is required")
    private String activity; // e.g., "Testing"

    @NotBlank(message = "Category is required")
    private String category; // e.g., "Quality Assurance"

    private String description;

    @NotNull(message = "Work hours are required")
    @DecimalMin(value = "0.1", message = "Work hours must be greater than zero")
    @DecimalMax(value = "24.0", message = "Work hours cannot exceed 24")
    private Double workHours;

    private LocalTime startTime;
    private LocalTime endTime;

    // ---------- Constructors ----------
    public ActivityRequest() {
    }

    public ActivityRequest(UUID projectId, UUID activityTypeId, String project, String activity,
            String category, String description, Double workHours,
            LocalTime startTime, LocalTime endTime) {
        this.projectId = projectId;
        this.activityTypeId = activityTypeId;
        this.project = project;
        this.activity = activity;
        this.category = category;
        this.description = description;
        this.workHours = workHours;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // ---------- Getters & Setters ----------

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

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getWorkHours() {
        return workHours;
    }

    public void setWorkHours(Double workHours) {
        this.workHours = workHours;
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

}
