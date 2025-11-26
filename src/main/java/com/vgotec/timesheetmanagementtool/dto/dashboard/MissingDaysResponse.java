package com.vgotec.timesheetmanagementtool.dto.dashboard;

import lombok.Data;
import java.util.List;

@Data
public class MissingDaysResponse {

    private int month;
    private int year;
    private List<String> missingDates; // "2025-11-04", "2025-11-07"
    private int totalMissing;
}
