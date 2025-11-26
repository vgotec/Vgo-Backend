package com.vgotec.timesheetmanagementtool.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "leave_status")
public class LeaveStatusEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "color_code", nullable = false)
    private String colorCode;

    // Getters and setters
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

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }
}
