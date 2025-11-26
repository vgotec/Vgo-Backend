package com.vgotec.timesheetmanagementtool.dto.dashboard;

import lombok.Data;

@Data
public class ProductivityResponse {

    private int workingDays;
    private double totalHoursWorked;
    private double expectedHours;
    private double productivityPercentage; // (worked / expected) * 100
    private String category; // LOW / MEDIUM / HIGH
}
