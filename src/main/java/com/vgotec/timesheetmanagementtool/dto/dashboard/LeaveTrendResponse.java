package com.vgotec.timesheetmanagementtool.dto.dashboard;

import lombok.Data;
import java.util.Map;

@Data
public class LeaveTrendResponse {
    private int year;
    private Map<Integer, Double> monthlyLeaveDays; // {1:2.0, 2:1.0, 3:0.0 ...}
}
