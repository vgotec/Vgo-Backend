package com.vgotec.timesheetmanagementtool.dto;

import com.vgotec.timesheetmanagementtool.enums.LeaveStatus;
import java.time.LocalDate;
import java.util.UUID;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class LeaveResponse {

    // NEW FIELD → to hold null OR leave payload
    private Object leave;

    // NEW FIELD → to hold form JSON if needed
    private Map<String, Object> formJson; // add this

    private UUID id;
    private String leaveTypeName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String session;
    private String remarks;
    private LeaveStatus status;
    private Double totalDays;
    private String message;

    public LeaveResponse() {
    }

    // existing constructor
    public LeaveResponse(UUID id, String leaveTypeName, LocalDate startDate,
            LocalDate endDate, String session, String remarks,
            LeaveStatus status, Double totalDays, String message) {
        this.id = id;
        this.leaveTypeName = leaveTypeName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.session = session;
        this.remarks = remarks;
        this.status = status;
        this.totalDays = totalDays;
        this.message = message;
    }

    // --------- NEW GETTERS & SETTERS ----------
    public Object getLeave() {
        return leave;
    }

    public void setLeave(Object leave) {
        this.leave = leave;
    }

    public Map<String, Object> getFormJson() {
        return formJson;
    }

    public void setFormJson(Map<String, Object> formJson) {
        this.formJson = formJson;
    }

    // --------- EXISTING GETTERS & SETTERS ----------
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLeaveTypeName() {
        return leaveTypeName;
    }

    public void setLeaveTypeName(String leaveTypeName) {
        this.leaveTypeName = leaveTypeName;
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

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public LeaveStatus getStatus() {
        return status;
    }

    public void setStatus(LeaveStatus status) {
        this.status = status;
    }

    public Double getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(Double totalDays) {
        this.totalDays = totalDays;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private Object leaveBalance;

    public Object getLeaveBalance() {
        return leaveBalance;
    }

    public void setLeaveBalance(Object leaveBalance) {
        this.leaveBalance = leaveBalance;
    }

}
