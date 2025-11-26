package com.vgotec.timesheetmanagementtool.service.interfaces;

import com.vgotec.timesheetmanagementtool.dto.dashboard.*;
import java.util.List;
import java.util.UUID;

public interface DashboardService {

    DailyHoursResponse getDailyHours(UUID empId, int month, int year);

    List<ProjectHoursResponse> getProjectHours(UUID empId, int month, int year);

    LeaveTrendResponse getLeaveTrend(UUID empId, int year);

    ProductivityResponse getProductivity(UUID empId, int month, int year);

    FocusAreaBreakdownResponse getFocusAreaBreakdown(UUID empId, int month, int year);

    WeeklyBurnRateResponse getWeeklyBurnRate(UUID projectId);

    List<ProjectLoadResponse> getProjectLoad(int month, int year);

    SessionBreakdownResponse getSessionBreakdown(UUID empId);

    MissingDaysResponse getMissingDays(UUID empId, int month, int year);

    DashboardResponse getDashboard(UUID empId, int month, int year);

    List<RecentActivityResponse> getRecentActivity(UUID empId);

}
