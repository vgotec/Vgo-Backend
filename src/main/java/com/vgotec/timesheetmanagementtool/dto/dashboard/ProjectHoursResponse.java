package com.vgotec.timesheetmanagementtool.dto.dashboard;

import lombok.Data;

@Data
public class ProjectHoursResponse {
    private String projectId;
    private String projectName;
    private double totalHours;
}
