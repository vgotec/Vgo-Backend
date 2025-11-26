package com.vgotec.timesheetmanagementtool.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class EmployeeLeaveBalanceResponse {

    private UUID leaveTypeId;
    private String leaveTypeName;
    private int year;
    private double usedDays;
    private double remainingDays;
    private LocalDateTime updatedAt;

    public EmployeeLeaveBalanceResponse(UUID leaveTypeId, String leaveTypeName, int year,
            double usedDays, double remainingDays, LocalDateTime updatedAt) {
        this.leaveTypeId = leaveTypeId;
        this.leaveTypeName = leaveTypeName;
        this.year = year;
        this.usedDays = usedDays;
        this.remainingDays = remainingDays;
        this.updatedAt = updatedAt;
    }

    // ✅ Getters and Setters
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getUsedDays() {
        return usedDays;
    }

    public void setUsedDays(double usedDays) {
        this.usedDays = usedDays;
    }

    public double getRemainingDays() {
        return remainingDays;
    }

    public void setRemainingDays(double remainingDays) {
        this.remainingDays = remainingDays;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
