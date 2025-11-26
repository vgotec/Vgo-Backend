package com.vgotec.timesheetmanagementtool.dto.dashboard;

import lombok.Data;
import java.util.Map;

@Data
public class FocusAreaBreakdownResponse {

    private Map<String, Double> dynamicFocusHours; // DEVELOPMENT: 5, TESTING: 3...
    private double totalHours;
}
