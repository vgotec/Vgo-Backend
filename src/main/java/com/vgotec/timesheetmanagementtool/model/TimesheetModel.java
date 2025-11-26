package com.vgotec.timesheetmanagementtool.model;

import com.vgotec.timesheetmanagementtool.enums.TimeSheetStatus;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * DTO model representing a single timesheet summary.
 * Used for API responses (fetch, list, update, etc.).
 */
public class TimesheetModel {

    private UUID id;
    private UUID employeeId;
    private LocalDate workDate;
    private Double totalHours;
    private String remarks;
    private String attachmentPath;
    private TimeSheetStatus status;
    private UUID approvedBy;
    private OffsetDateTime approvedAt;
    private Integer year;
    private Integer month;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    // ---------------- Constructors ----------------
    public TimesheetModel() {
    }

    public TimesheetModel(UUID id, UUID employeeId, LocalDate workDate,
            Double totalHours, String remarks, String attachmentPath,
            TimeSheetStatus status, UUID approvedBy, OffsetDateTime approvedAt,
            Integer year, Integer month, OffsetDateTime createdAt,
            OffsetDateTime updatedAt) {
        this.id = id;
        this.employeeId = employeeId;
        this.workDate = workDate;
        this.totalHours = totalHours;
        this.remarks = remarks;
        this.attachmentPath = attachmentPath;
        this.status = status;
        this.approvedBy = approvedBy;
        this.approvedAt = approvedAt;
        this.year = year;
        this.month = month;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // ---------------- Getters & Setters ----------------
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(UUID employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getWorkDate() {
        return workDate;
    }

    public void setWorkDate(LocalDate workDate) {
        this.workDate = workDate;
    }

    public Double getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(Double totalHours) {
        this.totalHours = totalHours;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getAttachmentPath() {
        return attachmentPath;
    }

    public void setAttachmentPath(String attachmentPath) {
        this.attachmentPath = attachmentPath;
    }

    public TimeSheetStatus getStatus() {
        return status;
    }

    public void setStatus(TimeSheetStatus status) {
        this.status = status;
    }

    public UUID getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(UUID approvedBy) {
        this.approvedBy = approvedBy;
    }

    public OffsetDateTime getApprovedAt() {
        return approvedAt;
    }

    public void setApprovedAt(OffsetDateTime approvedAt) {
        this.approvedAt = approvedAt;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
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
