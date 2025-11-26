package com.vgotec.timesheetmanagementtool.dto.dashboard;

import lombok.Data;
import java.util.Map;

@Data
public class SessionBreakdownResponse {

    private Map<String, Integer> sessionCount; // {FULL_DAY: 22, FIRST_HALF: 3}
    private Map<String, Double> sessionPercentage; // %
}
