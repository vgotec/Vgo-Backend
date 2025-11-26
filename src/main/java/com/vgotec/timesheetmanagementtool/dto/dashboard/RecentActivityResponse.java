package com.vgotec.timesheetmanagementtool.dto.dashboard;

import lombok.Data;
import java.time.OffsetDateTime;

@Data
public class RecentActivityResponse {
    private String activityId;
    private String activityType;
    private String activityDescription;
    private OffsetDateTime activityTime;
    private String status;
    private String remarks;
    private String activityIcon;
}
