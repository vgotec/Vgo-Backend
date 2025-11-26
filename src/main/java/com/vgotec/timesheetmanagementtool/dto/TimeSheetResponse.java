package com.vgotec.timesheetmanagementtool.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * DTO representing the response for a single timesheet.
 * Includes overall timesheet data and its related entries.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TimeSheetResponse {

    private UUID id;
    private LocalDate workDate;
    private String status;
    private Double totalHours;
    private List<TimeSheetEntryResponse> entries;
    private boolean isNew;
    private Map<String, Object> formJson;

    // ---------- Constructors ----------

    public TimeSheetResponse() {
    }

    public TimeSheetResponse(UUID id, LocalDate workDate, String status,
            Double totalHours, List<TimeSheetEntryResponse> entries) {
        this.id = id;
        this.workDate = workDate;
        this.status = status;
        this.totalHours = totalHours;
        this.entries = entries;
    }

    // ---------- Getters and Setters ----------

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getWorkDate() {
        return workDate;
    }

    public void setWorkDate(LocalDate workDate) {
        this.workDate = workDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(Double totalHours) {
        this.totalHours = totalHours;
    }

    public List<TimeSheetEntryResponse> getEntries() {
        return entries;
    }

    public void setEntries(List<TimeSheetEntryResponse> entries) {
        this.entries = entries;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

    // ---------- toString (Optional for Debugging) ----------
    public Map<String, Object> getFormJson() {
        return formJson;
    }

    public void setFormJson(Map<String, Object> formJson) {
        this.formJson = formJson;
    }

}
