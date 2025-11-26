package com.vgotec.timesheetmanagementtool.dto;

import com.vgotec.timesheetmanagementtool.enums.TimeSheetStatus;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * DTO for creating or updating a timesheet.
 * Contains the main timesheet details and list of related activity entries.
 */
public class TimeSheetRequest {

    @NotNull(message = "Employee ID is required")
    private UUID employeeId;

    @NotNull(message = "Work date is required")
    private LocalDate workDate;

    private String remarks;
    private String attachmentPath;

    private TimeSheetStatus status = TimeSheetStatus.NEW;

    /**
     * Each entry represents a project activity for the selected date.
     */
    @NotEmpty(message = "At least one activity entry must be provided")
    private List<ActivityRequest> entries;

    // ---------- Constructors ----------

    public TimeSheetRequest() {
    }

    public TimeSheetRequest(UUID employeeId, LocalDate workDate, String remarks,
            String attachmentPath, TimeSheetStatus status,
            List<ActivityRequest> entries) {
        this.employeeId = employeeId;
        this.workDate = workDate;
        this.remarks = remarks;
        this.attachmentPath = attachmentPath;
        this.status = status != null ? status : TimeSheetStatus.NEW;
        this.entries = entries;
    }

    // ---------- Getters and Setters ----------

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

    public List<ActivityRequest> getEntries() {
        return entries;
    }

    public void setEntries(List<ActivityRequest> entries) {
        this.entries = entries;
    }

    // ---------- toString (Optional for Logging) ----------

}
