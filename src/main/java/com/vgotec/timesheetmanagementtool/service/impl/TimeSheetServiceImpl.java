package com.vgotec.timesheetmanagementtool.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vgotec.timesheetmanagementtool.dto.*;
import com.vgotec.timesheetmanagementtool.entity.*;
import com.vgotec.timesheetmanagementtool.enums.TimeSheetStatus;
import com.vgotec.timesheetmanagementtool.repository.*;
import com.vgotec.timesheetmanagementtool.service.interfaces.TimeSheetService;
//import com.vgotec.timesheetmanagementtool.service.impl.TimesheetStatusColorService;
import com.vgotec.timesheetmanagementtool.validation.TimesheetValidation;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class TimeSheetServiceImpl implements TimeSheetService {

    private final TimeSheetRepository timeSheetRepository;
    private final TimeSheetEntryRepository timeSheetEntryRepository;
    private final ProjectRepository projectRepository;
    private final ActivityTypeRepository activityTypeRepository;
    private final TimesheetStatusColorService timesheetStatusColorService;

    public TimeSheetServiceImpl(TimeSheetRepository timeSheetRepository,
            TimeSheetEntryRepository timeSheetEntryRepository,
            ProjectRepository projectRepository,
            ActivityTypeRepository activityTypeRepository,
            TimesheetStatusColorService timesheetStatusColorService) {
        this.timeSheetRepository = timeSheetRepository;
        this.timeSheetEntryRepository = timeSheetEntryRepository;
        this.projectRepository = projectRepository;
        this.activityTypeRepository = activityTypeRepository;
        this.timesheetStatusColorService = timesheetStatusColorService;
    }

    /**
     * Create a new timesheet with its activity entries
     */
    @Override
    public TimeSheetResponse createTimeSheet(TimeSheetRequest requestDto) {
        Optional<TimeSheet> existing = timeSheetRepository.findByEmployeeIdAndWorkDate(
                requestDto.getEmployeeId(), requestDto.getWorkDate());

        if (existing.isPresent()) {
            throw new RuntimeException("Timesheet for this date already exists");
        }
        TimesheetValidation.validateEmployeeId(requestDto.getEmployeeId());

        TimeSheet timeSheet = new TimeSheet();
        timeSheet.setEmployeeId(requestDto.getEmployeeId());
        timeSheet.setWorkDate(requestDto.getWorkDate());
        timeSheet.setStatus(requestDto.getStatus() != null ? requestDto.getStatus() : TimeSheetStatus.NEW);
        timeSheet.setRemarks(requestDto.getRemarks());
        timeSheet.setTotalHours(requestDto.getEntries() != null
                ? requestDto.getEntries().stream().mapToDouble(ActivityRequest::getWorkHours).sum()
                : 0.0);
        timeSheet.setCreatedAt(OffsetDateTime.now());

        timeSheetRepository.save(timeSheet);

        if (requestDto.getEntries() != null && !requestDto.getEntries().isEmpty()) {
            List<TimesheetEntry> entries = requestDto.getEntries().stream()
                    .map(dto -> {
                        TimesheetEntry entry = new TimesheetEntry();
                        entry.setTimesheet(timeSheet);
                        entry.setProjectId(dto.getProjectId());
                        entry.setActivityTypeId(dto.getActivityTypeId());
                        entry.setActivityDescription(dto.getDescription());
                        entry.setDurationHours(dto.getWorkHours());
                        entry.setStartTime(dto.getStartTime());
                        entry.setEndTime(dto.getEndTime());
                        entry.setCreatedAt(OffsetDateTime.now());
                        entry.setUpdatedAt(OffsetDateTime.now());
                        return entry;
                    }).collect(Collectors.toList());

            timeSheetEntryRepository.saveAll(entries);
            timeSheet.setEntries(entries);
        }

        return mapToResponse(timeSheet, false);
    }

    /**
     * Update an existing timesheet
     */
    @Override
    public TimeSheetResponse updateTimeSheet(UUID id, TimeSheetRequest requestDto) {
        TimeSheet timeSheet = timeSheetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Timesheet not found"));

        timeSheet.setRemarks(requestDto.getRemarks());
        timeSheet.setStatus(requestDto.getStatus());
        timeSheet.setUpdatedAt(OffsetDateTime.now());

        if (requestDto.getEntries() != null && !requestDto.getEntries().isEmpty()) {
            timeSheetEntryRepository.deleteAll(timeSheet.getEntries());

            List<TimesheetEntry> newEntries = requestDto.getEntries().stream()
                    .map(dto -> {
                        TimesheetEntry entry = new TimesheetEntry();
                        entry.setTimesheet(timeSheet);
                        entry.setProjectId(dto.getProjectId());
                        entry.setActivityTypeId(dto.getActivityTypeId());
                        entry.setActivityDescription(dto.getDescription());
                        entry.setDurationHours(dto.getWorkHours());
                        entry.setStartTime(dto.getStartTime());
                        entry.setEndTime(dto.getEndTime());
                        entry.setCreatedAt(OffsetDateTime.now());
                        entry.setUpdatedAt(OffsetDateTime.now());
                        return entry;
                    }).collect(Collectors.toList());

            timeSheetEntryRepository.saveAll(newEntries);
            timeSheet.setEntries(newEntries);

            double total = newEntries.stream().mapToDouble(TimesheetEntry::getDurationHours).sum();
            timeSheet.setTotalHours(total);
        }

        timeSheetRepository.save(timeSheet);
        return mapToResponse(timeSheet, false);
    }

    @Transactional
    @Override
    public TimeSheetResponse updateStatus(UUID id, String newStatus) {
        TimeSheet timeSheet = timeSheetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Timesheet not found"));

        // ✅ Validation: Timesheet must contain activities
        if (timeSheet.getEntries() == null || timeSheet.getEntries().isEmpty()) {
            throw new RuntimeException("Cannot update status. No activities found in the timesheet.");
        }

        // ✅ Update status safely
        timeSheet.setStatus(TimeSheetStatus.valueOf(newStatus.toUpperCase()));
        timeSheet.setUpdatedAt(OffsetDateTime.now());
        timeSheetRepository.save(timeSheet);

        return mapToResponse(timeSheet, false);
    }

    /**
     * Get or create timesheet when user clicks a date (includes activity list)
     */
    @Override
    public TimeSheetResponse getOrCreateTimeSheet(UUID employeeId, LocalDate date) {
        Optional<TimeSheet> existingSheetOpt = timeSheetRepository.findByEmployeeIdAndWorkDate(employeeId, date);
        TimeSheet timeSheet;
        boolean isNew = false;
        TimesheetValidation.validateEmployeeId(employeeId);

        if (existingSheetOpt.isPresent()) {
            timeSheet = existingSheetOpt.get();
        } else {
            timeSheet = new TimeSheet();
            timeSheet.setEmployeeId(employeeId);
            timeSheet.setWorkDate(date);
            timeSheet.setStatus(TimeSheetStatus.NEW);
            timeSheet.setTotalHours(0.0);
            timeSheet.setCreatedAt(OffsetDateTime.now());
            timeSheet.setRemarks("Auto-created empty timesheet");
            timeSheet = timeSheetRepository.save(timeSheet);
            isNew = true;
        }

        // Fetch existing entries
        List<TimesheetEntry> entries = timeSheetEntryRepository.findByTimesheetId(timeSheet.getId());

        // Calculate total hours dynamically
        double totalHours = entries.stream()
                .mapToDouble(entry -> {
                    if (entry.getDurationHours() != null && entry.getDurationHours() > 0)
                        return entry.getDurationHours();
                    if (entry.getStartTime() != null && entry.getEndTime() != null) {
                        long minutes = Duration.between(entry.getStartTime(), entry.getEndTime()).toMinutes();
                        return minutes / 60.0;
                    }
                    return 0.0;
                })
                .sum();

        boolean updated = false;

        // ✅ Update total hours only if changed
        if (!Objects.equals(timeSheet.getTotalHours(), totalHours)) {
            timeSheet.setTotalHours(totalHours);
            updated = true;
        }

        // ✅ Only set NOT_SUBMITTED if needed (avoid repeated overwrite)
        if (totalHours > 0 &&
                (timeSheet.getStatus() == TimeSheetStatus.NEW ||
                        timeSheet.getStatus() == TimeSheetStatus.NONE)
                &&
                timeSheet.getStatus() != TimeSheetStatus.NOT_SUBMITTED) {

            timeSheet.setStatus(TimeSheetStatus.NOT_SUBMITTED);
            updated = true;
        }

        // ✅ Revert to NEW only if all activities are deleted
        else if (totalHours == 0 &&
                timeSheet.getStatus() == TimeSheetStatus.NOT_SUBMITTED) {

            timeSheet.setStatus(TimeSheetStatus.NEW);
            updated = true;
        }

        if (updated) {
            timeSheet.setUpdatedAt(OffsetDateTime.now());
            timeSheetRepository.save(timeSheet);
        }

        // ✅ Include form JSON from resources in response
        TimeSheetResponse response = mapToResponse(timeSheet, isNew);
        response.setFormJson(FormJsonCache.getTimesheetForm());
        return response;
    }

    /**
     * Helper method - Map entity to response DTO
     */
    private TimeSheetResponse mapToResponse(TimeSheet ts, boolean isNew) {
        TimeSheetResponse response = new TimeSheetResponse();
        response.setId(ts.getId());
        response.setWorkDate(ts.getWorkDate());
        response.setStatus(ts.getStatus().name());
        response.setTotalHours(ts.getTotalHours());
        response.setIsNew(isNew);

        List<TimeSheetEntryResponse> entryResponses = timeSheetEntryRepository.findByTimesheetId(ts.getId())
                .stream()
                .map(this::mapEntryToResponse)
                .collect(Collectors.toList());

        response.setEntries(entryResponses);
        return response;
    }

    /**
     * Converts TimesheetEntry entity to readable DTO
     */
    private TimeSheetEntryResponse mapEntryToResponse(TimesheetEntry e) {
        Project project = projectRepository.findById(e.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found for ID: " + e.getProjectId()));

        ActivityType activityType = activityTypeRepository.findById(e.getActivityTypeId())
                .orElseThrow(() -> new RuntimeException("ActivityType not found for ID: " + e.getActivityTypeId()));

        String fullProject = project.getProjectCode() + " - " + project.getProjectName();

        double duration = e.getDurationHours();
        if (duration == 0 && e.getStartTime() != null && e.getEndTime() != null) {
            long minutes = Duration.between(e.getStartTime(), e.getEndTime()).toMinutes();
            duration = minutes / 60.0;
        }

        return new TimeSheetEntryResponse(
                e.getId(),
                fullProject,
                activityType.getName(),
                activityType.getCategory(),
                e.getActivityDescription(),
                duration,
                e.getStartTime(),
                e.getEndTime(),
                e.getTimesheet() != null ? e.getTimesheet().getWorkDate() : null);
    }

    /**
     * Load timesheet form JSON from resources
     */
    public class FormJsonCache {
        private static Map<String, Object> timesheetFormJson;

        public static Map<String, Object> getTimesheetForm() {
            if (timesheetFormJson == null) {
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    ClassPathResource resource = new ClassPathResource("forms/timesheetform.json");
                    timesheetFormJson = mapper.readValue(resource.getInputStream(), new TypeReference<>() {
                    });
                } catch (Exception e) {
                    throw new RuntimeException("Failed to load form JSON", e);
                }
            }
            return timesheetFormJson;
        }
    }

    // @Override
    // public List<TimeSheetResponse> getTimeSheetsByDate(UUID employeeId, LocalDate
    // date) {
    // List<TimeSheet> sheets =
    // timeSheetRepository.findByEmployeeIdAndWorkDateBetween(employeeId, date,
    // date);
    // return sheets.stream().map(ts -> mapToResponse(ts,
    // false)).collect(Collectors.toList());
    // }

    // @Override
    // public List<TimeSheetResponse> getTimeSheetsByMonth(UUID employeeId, int
    // year, int month) {
    // List<TimeSheet> sheets = timeSheetRepository.findByEmployeeId(employeeId);
    // return sheets.stream()
    // .filter(ts -> ts.getWorkDate().getYear() == year &&
    // ts.getWorkDate().getMonthValue() == month)
    // .map(ts -> mapToResponse(ts, false))
    // .collect(Collectors.toList());
    // }

    @Override
    public void deleteTimeSheet(UUID id) {
        TimeSheet ts = timeSheetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Timesheet not found"));

        TimeSheetStatus status = ts.getStatus();

        // Allow only NEW or NOT_SUBMITTED
        if (status == TimeSheetStatus.NEW || status == TimeSheetStatus.NOT_SUBMITTED) {
            timeSheetRepository.deleteById(id);
            return;
        }

        throw new IllegalStateException(
                "Timesheet cannot be deleted because its status is: " + status +
                        ". Only NEW or NOT_SUBMITTED timesheets can be deleted.");
    }

    @Override
    public List<CalendarStatusResponse> getCalendarStatus(UUID employeeId, int year, int month) {
        List<TimeSheet> sheets = timeSheetRepository.findByEmployeeIdAndMonth(employeeId, year, month);

        Map<LocalDate, TimeSheetStatus> dateToStatus = sheets.stream()
                .collect(Collectors.toMap(
                        TimeSheet::getWorkDate,
                        TimeSheet::getStatus,
                        (s1, s2) -> s1));

        YearMonth yearMonth = YearMonth.of(year, month);
        List<CalendarStatusResponse> responses = new ArrayList<>();

        for (int day = 1; day <= yearMonth.lengthOfMonth(); day++) {
            LocalDate date = yearMonth.atDay(day);
            TimeSheetStatus status = dateToStatus.getOrDefault(date, null);

            String statusName = (status != null) ? status.name() : "NONE";
            String colorCode = "#E0E0E0"; // default grey for "NONE"

            if (status != null) {
                colorCode = timesheetStatusColorService.getColorForStatus(status);
            }

            responses.add(new CalendarStatusResponse(date, statusName, colorCode));
        }

        return responses;
    }

}
