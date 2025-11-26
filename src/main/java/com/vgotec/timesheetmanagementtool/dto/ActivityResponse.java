package com.vgotec.timesheetmanagementtool.dto;

import java.time.LocalTime;
import java.util.UUID;

/**
 * DTO for sending full activity response to frontend.
 * Combines projectCode + projectName as 'project' field.
 */
public class ActivityResponse {

    private UUID id;
    private String project; // 🆕 Combined format → "PRJ001 - Payroll System"
    private String activity;
    private String category;
    private String description;
    private Double workHours;
    private LocalTime startTime;
    private LocalTime endTime;

    // ---------- Constructors ----------
    public ActivityResponse() {
    }

    public ActivityResponse(UUID id, String project, String activity, String category,
            String description, Double workHours,
            LocalTime startTime, LocalTime endTime) {
        this.id = id;
        this.project = project;
        this.activity = activity;
        this.category = category;
        this.description = description;
        this.workHours = workHours;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // ---------- Getters & Setters ----------
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public String getdescription() {
        return description;
    }

    public void setdescription(String activityDescription) {
        this.description = activityDescription;
    }

    public Double getworkHours() {
        return workHours;
    }

    public void setworkHours(Double workHours) {
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
