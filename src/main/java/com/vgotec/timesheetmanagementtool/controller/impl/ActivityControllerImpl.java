package com.vgotec.timesheetmanagementtool.controller.impl;

import com.vgotec.timesheetmanagementtool.controller.interfaces.IActivityController;
import com.vgotec.timesheetmanagementtool.dto.ActivityRequest;
import com.vgotec.timesheetmanagementtool.dto.TimeSheetEntryResponse;
import com.vgotec.timesheetmanagementtool.service.interfaces.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
public class ActivityControllerImpl implements IActivityController {

    private final ActivityService activityService;

    @Autowired
    public ActivityControllerImpl(ActivityService activityService) {
        this.activityService = activityService;
    }

    /**
     * ➕ Create a new activity for an existing timesheet
     */
    @Override
    public ResponseEntity<TimeSheetEntryResponse> createActivity(UUID timesheetId, ActivityRequest request) {
        TimeSheetEntryResponse response = activityService.createActivity(request, timesheetId);
        return ResponseEntity.ok(response);
    }

    /**
     * ✏️ Update an existing activity
     */

    @Override
    @PutMapping("/update/{entryId}")
    public ResponseEntity<TimeSheetEntryResponse> updateActivity(
            @PathVariable UUID entryId,
            @RequestBody ActivityRequest request) {

        TimeSheetEntryResponse response = activityService.updateActivity(entryId, request);
        return ResponseEntity.ok(response);
    }

    /**
     * ❌ Delete an activity by ID
     */

    /**
     * 📄 Get all activities for a specific timesheet
     */
    @Override
    @DeleteMapping("delete/{entryId}")
    public ResponseEntity<String> deleteActivity(@PathVariable UUID entryId) {
        activityService.deleteActivity(entryId);
        return ResponseEntity.ok("Activity deleted successfully");
    }

    @PostMapping("/create/{employeeId}/{date}")
    public ResponseEntity<TimeSheetEntryResponse> createActivityByDate(
            @PathVariable UUID employeeId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestBody ActivityRequest request) {

        TimeSheetEntryResponse response = activityService.createActivityByDate(request, employeeId, date);
        return ResponseEntity.ok(response);
    }
}
