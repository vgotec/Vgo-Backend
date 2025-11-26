package com.vgotec.timesheetmanagementtool.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class LeaveModel {

    private UUID id;
    private UUID employeeId;
    private UUID leaveTypeId;
    private String leaveTypeName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String session;
    private String remarks;
    private String status;
    private Integer year;
    private Integer month;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Double totalDays;

    // ---------- Constructors ----------
    public LeaveModel() {
    }

    public LeaveModel(UUID id, UUID employeeId, UUID leaveTypeId, String leaveTypeName,
            LocalDate startDate, LocalDate endDate, String session, String remarks,
            String status, Integer year, Integer month,
            LocalDateTime createdAt, LocalDateTime updatedAt, Double totalDays) {
        this.id = id;
        this.employeeId = employeeId;
        this.leaveTypeId = leaveTypeId;
        this.leaveTypeName = leaveTypeName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.session = session;
        this.remarks = remarks;
        this.status = status;
        this.year = year;
        this.month = month;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.totalDays = totalDays;
    }

    // ---------- Getters & Setters ----------
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

    public UUID getLeaveTypeId() {
        return leaveTypeId;
    }

    public void setLeaveTypeId(UUID leaveTypeId) {
        this.leaveTypeId = leaveTypeId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Double getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(Double totalDays) {
        this.totalDays = totalDays;
    }
}
