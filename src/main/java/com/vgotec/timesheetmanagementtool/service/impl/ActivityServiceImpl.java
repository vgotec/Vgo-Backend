package com.vgotec.timesheetmanagementtool.service.impl;

import com.vgotec.timesheetmanagementtool.dto.ActivityRequest;
import com.vgotec.timesheetmanagementtool.dto.TimeSheetEntryResponse;
import com.vgotec.timesheetmanagementtool.dto.TimeSheetResponse;
import com.vgotec.timesheetmanagementtool.entity.*;
import com.vgotec.timesheetmanagementtool.enums.TimeSheetStatus;
import com.vgotec.timesheetmanagementtool.repository.*;
import com.vgotec.timesheetmanagementtool.service.interfaces.ActivityService;
import com.vgotec.timesheetmanagementtool.service.interfaces.TimeSheetService;
import com.vgotec.timesheetmanagementtool.validation.ActivityValidation;
import com.vgotec.timesheetmanagementtool.validation.TimesheetValidation;

import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.time.OffsetDateTime;
import java.time.LocalDate;
import java.time.Duration;
import java.util.List;
import java.util.UUID;

@Service
public class ActivityServiceImpl implements ActivityService {

        private final TimeSheetRepository timeSheetRepository;
        private final TimeSheetEntryRepository entryRepository;
        private final ProjectRepository projectRepository;
        private final ActivityTypeRepository activityTypeRepository;
        private final TimeSheetService timeSheetService;

        public ActivityServiceImpl(TimeSheetRepository timeSheetRepository,
                        TimeSheetEntryRepository entryRepository,
                        ProjectRepository projectRepository,
                        ActivityTypeRepository activityTypeRepository,
                        TimeSheetService timeSheetService) {
                this.timeSheetRepository = timeSheetRepository;
                this.entryRepository = entryRepository;
                this.projectRepository = projectRepository;
                this.activityTypeRepository = activityTypeRepository;
                this.timeSheetService = timeSheetService;
        }

        // ---------------------------------------------------------
        // CREATE ACTIVITY
        // ---------------------------------------------------------
        @Override
        public TimeSheetEntryResponse createActivity(ActivityRequest request, UUID timesheetId) {

                TimeSheet ts = timeSheetRepository.findById(timesheetId)
                                .orElseThrow(() -> new RuntimeException("Timesheet not found"));

                String[] parts = request.getProject().split(" - ");
                String projectCode = parts[0].trim();

                Project project = projectRepository.findByProjectCode(projectCode)
                                .orElseThrow(() -> new RuntimeException("Project not found for code: " + projectCode));

                ActivityType activityType = activityTypeRepository
                                .findByNameAndCategory(request.getActivity(), request.getCategory())
                                .orElseThrow(() -> new RuntimeException(
                                                "Activity type not found for: " + request.getActivity() + " ("
                                                                + request.getCategory() + ")"));

                // -------------------------------
                // VALIDATIONS
                // -------------------------------
                // -------------------------------
                // VALIDATION BLOCK
                // -------------------------------
                ActivityValidation.validateRequest(request);

                ActivityValidation.validateDurationMatching(
                                request.getWorkHours(),
                                request.getStartTime(),
                                request.getEndTime());

                List<TimesheetEntry> existingEntries = entryRepository.findByTimesheetId(timesheetId);

                // 1) Validate time range (null, order, max 8 hours per activity)
                TimesheetValidation.validateTimeRange(request.getStartTime(), request.getEndTime());

                // 2) Check for overlapping activities
                TimesheetValidation.validateOverlap(request.getStartTime(), request.getEndTime(), existingEntries);

                // 3) Validate total daily hours (must not exceed 8 hours)
                TimesheetValidation.validateTotalHours(null, request.getWorkHours(), existingEntries);

                // -------------------------------
                // Save entry
                // -------------------------------
                TimesheetEntry entry = new TimesheetEntry();
                entry.setTimesheet(ts);
                entry.setProjectId(project.getId());
                entry.setActivityTypeId(activityType.getId());
                entry.setActivityDescription(request.getDescription());
                entry.setDurationHours(request.getWorkHours());
                entry.setStartTime(request.getStartTime());
                entry.setEndTime(request.getEndTime());
                entry.setCreatedAt(OffsetDateTime.now());
                entry.setUpdatedAt(OffsetDateTime.now());

                entryRepository.save(entry);

                double totalHours = entryRepository.findByTimesheetId(timesheetId)
                                .stream()
                                .mapToDouble(TimesheetEntry::getDurationHours)
                                .sum();

                ts.setTotalHours(totalHours);
                ts.setUpdatedAt(OffsetDateTime.now());
                timeSheetRepository.save(ts);

                String fullProject = project.getProjectCode() + " - " + project.getProjectName();

                return new TimeSheetEntryResponse(
                                entry.getId(),
                                fullProject,
                                activityType.getName(),
                                activityType.getCategory(),
                                entry.getActivityDescription(),
                                entry.getDurationHours(),
                                entry.getStartTime(),
                                entry.getEndTime(),
                                ts.getWorkDate());
        }

        // ---------------------------------------------------------
        // UPDATE ACTIVITY
        // ---------------------------------------------------------
        @Transactional
        @Override
        public TimeSheetEntryResponse updateActivity(UUID entryId, ActivityRequest request) {

                TimesheetEntry entry = entryRepository.findById(entryId)
                                .orElseThrow(() -> new RuntimeException("Activity not found"));

                String[] parts = request.getProject().split(" - ");
                String projectCode = parts[0].trim();

                Project project = projectRepository.findByProjectCode(projectCode)
                                .orElseThrow(() -> new RuntimeException("Project not found for code: " + projectCode));

                ActivityType activityType = activityTypeRepository
                                .findByNameAndCategory(request.getActivity(), request.getCategory())
                                .orElseThrow(() -> new RuntimeException(
                                                "Activity type not found for: " + request.getActivity() + " ("
                                                                + request.getCategory() + ")"));

                List<TimesheetEntry> existingEntries = entryRepository.findByTimesheetId(entry.getTimesheet().getId());

                // -------------------------------
                // VALIDATIONS
                // -------------------------------
                ActivityValidation.validateRequest(request);
                ActivityValidation.validateDurationMatching(
                                request.getWorkHours(),
                                request.getStartTime(),
                                request.getEndTime());

                TimesheetValidation.validateTimeRange(request.getStartTime(), request.getEndTime());

                TimesheetValidation.validateOverlap(request.getStartTime(), request.getEndTime(), existingEntries);

                TimesheetValidation.validateTotalHours(entryId, request.getWorkHours(), existingEntries);

                // -------------------------------
                // Update entry
                // -------------------------------
                entry.setProjectId(project.getId());
                entry.setActivityTypeId(activityType.getId());
                entry.setActivityDescription(request.getDescription());
                entry.setDurationHours(request.getWorkHours());
                entry.setStartTime(request.getStartTime());
                entry.setEndTime(request.getEndTime());
                entry.setUpdatedAt(OffsetDateTime.now());

                entryRepository.save(entry);

                String fullProject = project.getProjectCode() + " - " + project.getProjectName();

                return new TimeSheetEntryResponse(
                                entry.getId(),
                                fullProject,
                                activityType.getName(),
                                activityType.getCategory(),
                                entry.getActivityDescription(),
                                entry.getDurationHours(),
                                entry.getStartTime(),
                                entry.getEndTime(),
                                entry.getTimesheet().getWorkDate());
        }

        // ---------------------------------------------------------
        // DELETE ACTIVITY
        // ---------------------------------------------------------
        @Override
        public void deleteActivity(UUID entryId) {
                if (!entryRepository.existsById(entryId)) {
                        throw new RuntimeException("Activity not found");
                }
                entryRepository.deleteById(entryId);
        }

        // ---------------------------------------------------------
        // CREATE BY DATE
        // ---------------------------------------------------------
        @Override
        public TimeSheetEntryResponse createActivityByDate(ActivityRequest request, UUID employeeId, LocalDate date) {

                TimeSheetResponse tsResponse = timeSheetService.getOrCreateTimeSheet(employeeId, date);

                TimeSheet timesheet = timeSheetRepository.findById(tsResponse.getId())
                                .orElseThrow(() -> new RuntimeException("Timesheet not found"));

                return saveActivityEntry(timesheet, request);
        }

        // ---------------------------------------------------------
        // SHARED SAVE LOGIC
        // ---------------------------------------------------------
        private TimeSheetEntryResponse saveActivityEntry(TimeSheet timesheet, ActivityRequest request) {

                String[] parts = request.getProject().split(" - ");
                String projectCode = parts[0].trim();

                Project project = projectRepository.findByProjectCode(projectCode)
                                .orElseThrow(() -> new RuntimeException("Project not found for code: " + projectCode));

                ActivityType activityType = activityTypeRepository
                                .findByNameAndCategory(request.getActivity(), request.getCategory())
                                .orElseThrow(() -> new RuntimeException(
                                                "Activity type not found for: " + request.getActivity() + " ("
                                                                + request.getCategory() + ")"));

                // -------------------------------------------------
                // VALIDATION
                // -------------------------------------------------
                List<TimesheetEntry> existingEntries = entryRepository.findByTimesheetId(timesheet.getId());

                TimesheetValidation.validateTimeRange(request.getStartTime(), request.getEndTime());

                TimesheetValidation.validateOverlap(request.getStartTime(), request.getEndTime(), existingEntries);

                // Duration calculation for save-by-date
                double durationHours = request.getWorkHours();
                if (durationHours == 0 && request.getStartTime() != null && request.getEndTime() != null) {
                        durationHours = Duration.between(request.getStartTime(), request.getEndTime()).toMinutes()
                                        / 60.0;
                }

                TimesheetValidation.validateTotalHours(null, durationHours, existingEntries);

                // if (timesheet.getStatus() == TimeSheetStatus.SUBMITTED) {
                // throw new RuntimeException("Cannot create new activity. Timesheet is already
                // submitted.");
                // }

                // -------------------------------------------------
                // SAVE
                // -------------------------------------------------
                TimesheetEntry entry = new TimesheetEntry();
                entry.setTimesheet(timesheet);
                entry.setProjectId(project.getId());
                entry.setActivityTypeId(activityType.getId());
                entry.setActivityDescription(request.getDescription());
                entry.setDurationHours(durationHours);
                entry.setStartTime(request.getStartTime());
                entry.setEndTime(request.getEndTime());
                entry.setCreatedAt(OffsetDateTime.now());
                entry.setUpdatedAt(OffsetDateTime.now());

                entryRepository.save(entry);

                double totalHours = entryRepository.findByTimesheetId(timesheet.getId())
                                .stream()
                                .mapToDouble(TimesheetEntry::getDurationHours)
                                .sum();

                timesheet.setTotalHours(totalHours);
                timesheet.setUpdatedAt(OffsetDateTime.now());
                timeSheetRepository.save(timesheet);

                String fullProject = project.getProjectCode() + " - " + project.getProjectName();

                return new TimeSheetEntryResponse(
                                entry.getId(),
                                fullProject,
                                activityType.getName(),
                                activityType.getCategory(),
                                entry.getActivityDescription(),
                                entry.getDurationHours(),
                                entry.getStartTime(),
                                entry.getEndTime(),
                                timesheet.getWorkDate());
        }
}
