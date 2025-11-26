package com.vgotec.timesheetmanagementtool.dto.dashboard;

import lombok.Data;
import java.util.Map;

@Data
public class DailyHoursResponse {

    // Map<DayOfMonth, Map<ActivityName, Hours>>
    private Map<Integer, Map<String, Double>> dailyActivityHours;

    private double totalHoursWorked;
}
