package com.vgotec.timesheetmanagementtool.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class TimeSheetEntryResponse {

    private UUID id;
    private String project; // "PRJ001 - Payroll System"
    private String activity; // "Testing"
    private String category; // "Quality Assurance"
    private String description;
    private Double workHours;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate workDate; // ✅ Renamed for clarity

    // ---------- Constructors ----------
    public TimeSheetEntryResponse() {
    }

    public TimeSheetEntryResponse(UUID id, String project, String activity, String category,
            String description, Double workHours,
            LocalTime startTime, LocalTime endTime, LocalDate workDate) {
        this.id = id;
        this.project = project;
        this.activity = activity;
        this.category = category;
        this.description = description;
        this.workHours = workHours;
        this.startTime = startTime;
        this.endTime = endTime;
        this.workDate = workDate;
    }

    // ---------- Getters and Setters ----------
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

    public String getDescription() { // ✅ Fixed naming convention
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getWorkHours() { // ✅ Fixed naming convention
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

    public LocalDate getWorkDate() {
        return workDate;
    }

    public void setWorkDate(LocalDate workDate) {
        this.workDate = workDate;
    }
}
