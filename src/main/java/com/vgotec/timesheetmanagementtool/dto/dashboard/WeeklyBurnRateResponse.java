package com.vgotec.timesheetmanagementtool.dto.dashboard;

import lombok.Data;
import java.util.List;

@Data
public class WeeklyBurnRateResponse {

    private List<Integer> weekNumbers; // 1–52
    private List<Double> weeklyHours; // Hours per week
}
