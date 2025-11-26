package com.vgotec.timesheetmanagementtool.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "timesheet_status_color")
public class TimesheetStatusColor {

    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, unique = true)
    private com.vgotec.timesheetmanagementtool.enums.TimeSheetStatus status;

    @Column(name = "color_code", nullable = false)
    private String colorCode;

    public TimesheetStatusColor() {
    }

    public TimesheetStatusColor(com.vgotec.timesheetmanagementtool.enums.TimeSheetStatus status, String colorCode) {
        this.status = status;
        this.colorCode = colorCode;
    }

    public UUID getId() {
        return id;
    }

    public com.vgotec.timesheetmanagementtool.enums.TimeSheetStatus getStatus() {
        return status;
    }

    public void setStatus(com.vgotec.timesheetmanagementtool.enums.TimeSheetStatus status) {
        this.status = status;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }
}
