package com.vgotec.timesheetmanagementtool.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import com.vgotec.timesheetmanagementtool.enums.LeaveStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "leaves")
public class Leave {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "employee_id", nullable = false)
    private UUID employeeId;

    @Column(name = "leave_type_id")
    private UUID leaveTypeId;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(length = 20)
    private String session = "FULL_DAY"; // default handled in DB

    @Column(length = 300)
    private String remarks;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private LeaveStatus status = LeaveStatus.NEW;
    // default handled in DB

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    @Column(name = "year", insertable = false, updatable = false)
    private Integer year; // generated automatically

    @Column(name = "month", insertable = false, updatable = false)
    private Integer month; // generated automatically

    // ---------- Constructors ----------
    public Leave() {
    }

    public Leave(UUID id, UUID employeeId, UUID leaveTypeId, LocalDate startDate,
            LocalDate endDate, String session, String remarks, LeaveStatus status,
            LocalDateTime createdAt, LocalDateTime updatedAt, Integer year, Integer month) {
        this.id = id;
        this.employeeId = employeeId;
        this.leaveTypeId = leaveTypeId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.session = session;
        this.remarks = remarks;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.year = year;
        this.month = month;
    }

    // ---------- Getters and Setters ----------
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
}
