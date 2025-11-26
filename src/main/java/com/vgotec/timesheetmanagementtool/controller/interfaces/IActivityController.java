package com.vgotec.timesheetmanagementtool.controller.interfaces;

import com.vgotec.timesheetmanagementtool.dto.ActivityRequest;
import com.vgotec.timesheetmanagementtool.dto.TimeSheetEntryResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RequestMapping("/api/activities")
public interface IActivityController {

        /**
         * ➕ Create a new activity for an existing timesheet (by timesheetId)
         */
        @PostMapping("/create/{timesheetId}")
        ResponseEntity<TimeSheetEntryResponse> createActivity(
                        @PathVariable UUID timesheetId,
                        @RequestBody ActivityRequest request);

        /**
         * ➕ Create a new activity using employeeId and date
         * ✅ This endpoint automatically finds or creates the timesheet for that date
         */
        @PostMapping("/create/{employeeId}/{date}")
        ResponseEntity<TimeSheetEntryResponse> createActivityByDate(
                        @PathVariable UUID employeeId,
                        @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                        @RequestBody ActivityRequest request);

        /**
         * ✏️ Update an existing activity
         */
        @PutMapping("/update/{entryId}")
        ResponseEntity<TimeSheetEntryResponse> updateActivity(
                        @PathVariable UUID entryId,
                        @RequestBody ActivityRequest request);

        /**
         * ❌ Delete an activity by ID
         */
        @DeleteMapping("/delete/{entryId}")
        ResponseEntity<String> deleteActivity(@PathVariable UUID entryId);
}
