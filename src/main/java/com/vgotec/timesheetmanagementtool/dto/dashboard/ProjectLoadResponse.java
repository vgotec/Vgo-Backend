package com.vgotec.timesheetmanagementtool.dto.dashboard;

import lombok.Data;

@Data
public class ProjectLoadResponse {

    private String projectId;
    private String projectName;

    private double developmentHours;
    private double testingHours;
    private double documentationHours;
    private double meetingHours;

    private double totalHours;
}
