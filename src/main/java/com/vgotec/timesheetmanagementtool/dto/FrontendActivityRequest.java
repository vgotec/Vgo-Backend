package com.vgotec.timesheetmanagementtool.dto;

import jakarta.validation.constraints.*;
import java.time.LocalTime;

/**
 * DTO received directly from frontend.
 */
public class FrontendActivityRequest {

    @NotBlank(message = "Project is required")
    private String project;

    @NotBlank(message = "Activity is required")
    private String activity;

    @NotBlank(message = "Category is required")
    private String category; // 🆕 Added field

    private String description;

    @NotNull(message = "Work hours are required")
    @DecimalMin(value = "0.1", message = "Work hours must be greater than zero")
    @DecimalMax(value = "24.0", message = "Work hours cannot exceed 24")
    private Double workHours;

    private LocalTime startTime;
    private LocalTime endTime;

    // ---------- Getters and Setters ----------
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
