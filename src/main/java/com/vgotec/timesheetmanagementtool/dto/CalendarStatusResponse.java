package com.vgotec.timesheetmanagementtool.dto;

import java.time.LocalDate;

public class CalendarStatusResponse {
    private LocalDate date;
    private String status;
    private String colorCode;

    public CalendarStatusResponse(LocalDate date, String status, String colorCode) {
        this.date = date;
        this.status = status;
        this.colorCode = colorCode;
    }

    // existing constructor for backward compatibility
    public CalendarStatusResponse(LocalDate date, String status) {
        this.date = date;
        this.status = status;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }
}
