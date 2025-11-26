package com.vgotec.timesheetmanagementtool.dto;

import java.time.LocalDate;

public class CalendarLeaveStatusResponse {
    private LocalDate date;
    private String status;
    private String colorCode;

    public CalendarLeaveStatusResponse(LocalDate date, String status, String colorCode) {
        this.date = date;
        this.status = status;
        this.colorCode = colorCode;
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
}
