package com.vgotec.timesheetmanagementtool.dto.dashboard;

import lombok.Data;
import java.util.List;

import com.vgotec.timesheetmanagementtool.dto.EmployeeLeaveBalanceResponse;

@Data
public class DashboardResponse {

    private DailyHoursResponse dailyHours;

    private List<ProjectHoursResponse> projectHours;

    private LeaveTrendResponse leaveTrend;

    private ProductivityResponse productivity;

    private FocusAreaBreakdownResponse focusAreaBreakdown;

    private WeeklyBurnRateResponse weeklyBurnRate; // only if needed

    private List<ProjectLoadResponse> projectLoad;

    private MissingDaysResponse missingDays;

    private List<EmployeeLeaveBalanceResponse> leaveBalance;
    private List<RecentActivityResponse> recentActivity;
    private List<SessionBreakdownResponse> sessionBreakdown;

}
