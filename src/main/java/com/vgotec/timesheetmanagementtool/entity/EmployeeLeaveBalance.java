package com.vgotec.timesheetmanagementtool.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "employee_leave_balance")
public class EmployeeLeaveBalance {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "employee_id", nullable = false)
    private UUID employeeId;

    @Column(name = "leave_type_id", nullable = false)
    private UUID leaveTypeId;

    @Column(nullable = false)
    private int year;

    @Column(name = "used_days")
    private double usedDays;

    @Column(name = "remaining_days")
    private double remainingDays;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    // ✅ Getters and Setters

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
