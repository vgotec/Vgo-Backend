package com.vgotec.timesheetmanagementtool.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import java.util.UUID;

@Entity
@Table(name = "leave_types")
public class LeaveType {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(length = 50, nullable = false, unique = true)
    private String name;

    @Column(name = "yearly_limit")
    private Double yearlyLimit = 12.0;

    // ---------- Constructors ----------
    public LeaveType() {
    }

    public LeaveType(UUID id, String name, Double yearlyLimit) {
        this.id = id;
        this.name = name;
        this.yearlyLimit = yearlyLimit;
    }

    // ---------- Getters and Setters ----------
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getYearlyLimit() {
        return yearlyLimit;
    }

    public void setYearlyLimit(Double yearlyLimit) {
        this.yearlyLimit = yearlyLimit;
    }

}
