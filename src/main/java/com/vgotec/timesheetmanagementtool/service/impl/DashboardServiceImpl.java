package com.vgotec.timesheetmanagementtool.service.impl;

import com.vgotec.timesheetmanagementtool.dto.dashboard.*;
import com.vgotec.timesheetmanagementtool.entity.TimesheetEntry;
import com.vgotec.timesheetmanagementtool.repository.*;
import com.vgotec.timesheetmanagementtool.service.interfaces.DashboardService;
import com.vgotec.timesheetmanagementtool.service.interfaces.LeaveService;
import com.vgotec.timesheetmanagementtool.util.DashboardUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private TimeSheetEntryRepository entryRepo;

    @Autowired
    private TimeSheetRepository tsRepo;

    @Autowired
    private LeaveRepository leaveRepo;

    @Autowired
    private ProjectRepository projectRepo;

    @Autowired
    private ActivityTypeRepository activityTypeRepository;

    @Autowired
    private LeaveService leaveService;

    // ========================================================================
    // 1. DAILY HOURS (Stacked Bar Graph)
    // ========================================================================
    @Override
    public DailyHoursResponse getDailyHours(UUID empId, int month, int year) {

        List<TimesheetEntry> entries = entryRepo.findByEmployeeAndMonth(empId, year, month);

        Map<Integer, Map<String, Double>> result = new HashMap<>();
        double totalHours = 0.0;

        for (TimesheetEntry e : entries) {

            int day = e.getTimesheet().getWorkDate().getDayOfMonth();
            String activityName = getActivityName(e.getActivityTypeId());
            double hrs = e.getDurationHours();

            result.putIfAbsent(day, new HashMap<>());

            Map<String, Double> dayActivities = result.get(day);
            dayActivities.put(activityName, dayActivities.getOrDefault(activityName, 0.0) + hrs);

            totalHours += hrs;
        }

        // Use utility to fill remaining days
        DashboardUtil.fillMissingDays(result, year, month);

        DailyHoursResponse response = new DailyHoursResponse();
        response.setDailyActivityHours(result);
        response.setTotalHoursWorked(totalHours);
        return response;
    }

    private String getActivityName(UUID activityTypeId) {
        if (activityTypeId == null)
            return "Other";

        return activityTypeRepository.findById(activityTypeId)
                .map(a -> a.getName())
                .orElse("Other");
    }

    // ========================================================================
    // 2. PROJECT HOURS → Horizontal Bar Graph
    // ========================================================================
    @Override
    public List<ProjectHoursResponse> getProjectHours(UUID empId, int month, int year) {

        List<Object[]> rows = entryRepo.getProjectHours(empId, year, month);
        List<ProjectHoursResponse> list = new ArrayList<>();

        for (Object[] r : rows) {
            UUID pid = (UUID) r[0];
            double hrs = ((Number) r[1]).doubleValue();

            ProjectHoursResponse dto = new ProjectHoursResponse();
            dto.setProjectId(pid.toString());
            dto.setProjectName(projectRepo.findById(pid)
                    .map(p -> p.getProjectName())
                    .orElse("Unknown Project"));
            dto.setTotalHours(hrs);

            list.add(dto);
        }

        return list;
    }

    // ========================================================================
    // 3. LEAVE TREND → Line Graph
    // ========================================================================
    @Override
    public LeaveTrendResponse getLeaveTrend(UUID empId, int year) {

        List<Object[]> rows = leaveRepo.getLeaveTrend(empId, year);
        Map<Integer, Double> map = new HashMap<>();

        for (Object[] r : rows) {
            map.put(((Number) r[0]).intValue(), ((Number) r[1]).doubleValue());
        }

        // Use util to fill missing months
        DashboardUtil.fillMissingMonths(map);

        LeaveTrendResponse res = new LeaveTrendResponse();
        res.setYear(year);
        res.setMonthlyLeaveDays(map);

        return res;
    }

    // ========================================================================
    // 4. PRODUCTIVITY → Gauge
    // ========================================================================
    @Override
    public ProductivityResponse getProductivity(UUID empId, int month, int year) {

        List<TimesheetEntry> entries = entryRepo.findByEmployeeAndMonth(empId, year, month);
        double total = entries.stream()
                .mapToDouble(TimesheetEntry::getDurationHours)
                .sum();

        int workingDays = DashboardUtil.countWorkingDays(year, month);
        double expected = workingDays * 8;
        double pct = expected == 0 ? 0 : (total / expected) * 100;

        String category = pct >= 90 ? "HIGH" : pct >= 60 ? "MEDIUM" : "LOW";

        ProductivityResponse res = new ProductivityResponse();
        res.setWorkingDays(workingDays);
        res.setTotalHoursWorked(total);
        res.setExpectedHours(expected);
        res.setProductivityPercentage(pct);
        res.setCategory(category);

        return res;
    }

    // ========================================================================
    // 5. FOCUS AREA BREAKDOWN → Donut Chart
    // ========================================================================
    @Override
    public FocusAreaBreakdownResponse getFocusAreaBreakdown(UUID empId, int month, int year) {

        List<TimesheetEntry> entries = entryRepo.findByEmployeeAndMonth(empId, year, month);

        Map<String, Double> categoryHours = DashboardUtil.createCategoryMap();

        for (TimesheetEntry e : entries) {
            UUID typeId = e.getActivityTypeId();
            if (typeId == null)
                continue;

            double hrs = e.getDurationHours();

            String category = activityTypeRepository.findById(typeId)
                    .map(a -> a.getCategory())
                    .orElse("OTHER")
                    .toUpperCase();

            categoryHours.put(category, categoryHours.getOrDefault(category, 0.0) + hrs);
        }

        FocusAreaBreakdownResponse res = new FocusAreaBreakdownResponse();
        res.setDynamicFocusHours(categoryHours);
        res.setTotalHours(categoryHours.values().stream().mapToDouble(Double::doubleValue).sum());

        return res;
    }

    // ========================================================================
    // 6. WEEKLY BURN RATE → Burn Down
    // ========================================================================
    @Override
    public WeeklyBurnRateResponse getWeeklyBurnRate(UUID projectId) {

        List<Object[]> rows = entryRepo.getWeeklyBurnRate(projectId);

        List<Integer> weeks = new ArrayList<>();
        List<Double> hours = new ArrayList<>();

        for (Object[] r : rows) {
            weeks.add(((Number) r[0]).intValue());
            hours.add(((Number) r[1]).doubleValue());
        }

        WeeklyBurnRateResponse res = new WeeklyBurnRateResponse();
        res.setWeekNumbers(weeks);
        res.setWeeklyHours(hours);

        return res;
    }

    // ========================================================================
    // 7. PROJECT LOAD → Stacked Bar Chart
    // ========================================================================
    @Override
    public List<ProjectLoadResponse> getProjectLoad(int month, int year) {

        List<Object[]> rows = entryRepo.getProjectLoad(year, month);
        Map<UUID, ProjectLoadResponse> map = new HashMap<>();

        for (Object[] r : rows) {

            UUID projectId = (UUID) r[0];
            String activity = ((String) r[1]).toLowerCase();
            double hrs = ((Number) r[2]).doubleValue();

            ProjectLoadResponse dto = map.getOrDefault(projectId, new ProjectLoadResponse());
            dto.setProjectId(projectId.toString());
            dto.setProjectName(projectRepo.findById(projectId)
                    .map(p -> p.getProjectName())
                    .orElse("Unknown"));

            if (activity.contains("dev"))
                dto.setDevelopmentHours(dto.getDevelopmentHours() + hrs);
            else if (activity.contains("test"))
                dto.setTestingHours(dto.getTestingHours() + hrs);
            else if (activity.contains("doc"))
                dto.setDocumentationHours(dto.getDocumentationHours() + hrs);
            else
                dto.setMeetingHours(dto.getMeetingHours() + hrs);

            dto.setTotalHours(dto.getDevelopmentHours()
                    + dto.getTestingHours()
                    + dto.getDocumentationHours()
                    + dto.getMeetingHours());

            map.put(projectId, dto);
        }

        return new ArrayList<>(map.values());
    }

    // ========================================================================
    // 8. SESSION BREAKDOWN → Pie Chart (no data)
    // ========================================================================
    @Override
    public SessionBreakdownResponse getSessionBreakdown(UUID empId) {

        SessionBreakdownResponse res = new SessionBreakdownResponse();
        res.setSessionCount(new HashMap<>());
        res.setSessionPercentage(new HashMap<>());
        return res;
    }

    // ========================================================================
    // 9. MISSING DAYS
    // ========================================================================
    @Override
    public MissingDaysResponse getMissingDays(UUID empId, int month, int year) {

        List<LocalDate> submitted = tsRepo.findSubmittedDays(empId, year, month);
        Set<LocalDate> submittedSet = new HashSet<>(submitted);

        int days = YearMonth.of(year, month).lengthOfMonth();
        List<String> missing = new ArrayList<>();

        for (int d = 1; d <= days; d++) {
            LocalDate date = LocalDate.of(year, month, d);
            DayOfWeek dow = date.getDayOfWeek();

            if (dow == DayOfWeek.SATURDAY || dow == DayOfWeek.SUNDAY)
                continue;

            if (!submittedSet.contains(date))
                missing.add(date.toString());
        }

        MissingDaysResponse res = new MissingDaysResponse();
        res.setMonth(month);
        res.setYear(year);
        res.setMissingDates(missing);
        res.setTotalMissing(missing.size());

        return res;
    }

    // ========================================================================
    // 10. MAIN DASHBOARD RESPONSE
    // ========================================================================
    @Override
    public DashboardResponse getDashboard(UUID empId, int month, int year) {

        DashboardResponse response = new DashboardResponse();

        response.setDailyHours(getDailyHours(empId, month, year));

        List<ProjectHoursResponse> projectHours = getProjectHours(empId, month, year);
        response.setProjectHours(projectHours);

        if (!projectHours.isEmpty()) {
            UUID projectId = UUID.fromString(projectHours.get(0).getProjectId());
            response.setWeeklyBurnRate(getWeeklyBurnRate(projectId));
        }

        response.setLeaveTrend(getLeaveTrend(empId, year));
        response.setProductivity(getProductivity(empId, month, year));
        response.setFocusAreaBreakdown(getFocusAreaBreakdown(empId, month, year));
        response.setProjectLoad(getProjectLoad(month, year));
        response.setMissingDays(getMissingDays(empId, month, year));
        response.setLeaveBalance(leaveService.getEmployeeLeaveBalances(empId));
        // response.setRecentActivity(getRecentActivity(empId));

        return response;
    }

    // ========================================================================
    // 11. RECENT ACTIVITY
    // ========================================================================
    @Override
    public List<RecentActivityResponse> getRecentActivity(UUID empId) {

        List<RecentActivityResponse> list = new ArrayList<>();

        List<Object[]> tsRows = tsRepo.getRecentTimesheetActivity(empId);

        for (Object[] r : tsRows) {
            RecentActivityResponse dto = new RecentActivityResponse();

            dto.setActivityId(r[0].toString());
            dto.setActivityType("TIMESHEET");
            dto.setStatus(r[2].toString());
            dto.setActivityTime(DashboardUtil.toOffset(r[4]));
            dto.setRemarks(r[5] != null ? r[5].toString() : "");

            LocalDate workDate = (LocalDate) r[3];
            dto.setActivityDescription("Timesheet " + dto.getStatus().toLowerCase() +
                    " for " + workDate);

            dto.setActivityIcon(
                    dto.getStatus().equals("APPROVED") ? "check"
                            : dto.getStatus().equals("REJECTED") ? "close"
                                    : "clock");

            list.add(dto);
        }

        List<Object[]> leaveRows = leaveRepo.getRecentLeaveActivity(empId);

        for (Object[] r : leaveRows) {
            RecentActivityResponse dto = new RecentActivityResponse();

            dto.setActivityId(r[0].toString());
            dto.setActivityType("LEAVE");
            dto.setStatus(r[3].toString());
            dto.setActivityTime(DashboardUtil.toOffset(r[4]));
            dto.setRemarks(r[5] != null ? r[5].toString() : "");

            dto.setActivityDescription(
                    "Leave " + r[2] + " " + dto.getStatus().toLowerCase());

            dto.setActivityIcon(
                    dto.getStatus().equals("APPROVED") ? "check"
                            : dto.getStatus().equals("REJECTED") ? "close"
                                    : "calendar");

            list.add(dto);
        }

        list.sort((a, b) -> b.getActivityTime().compareTo(a.getActivityTime()));

        return list;
    }
}
