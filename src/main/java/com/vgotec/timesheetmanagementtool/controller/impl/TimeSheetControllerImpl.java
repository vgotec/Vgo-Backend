package com.vgotec.timesheetmanagementtool.controller.impl;

import com.vgotec.timesheetmanagementtool.controller.interfaces.ITimeSheetController;
import com.vgotec.timesheetmanagementtool.dto.CalendarStatusResponse;
import com.vgotec.timesheetmanagementtool.dto.TimeSheetRequest;
import com.vgotec.timesheetmanagementtool.dto.TimeSheetResponse;
import com.vgotec.timesheetmanagementtool.service.interfaces.TimeSheetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.Map;

@RestController
@RequestMapping("/api/timesheet")
public class TimeSheetControllerImpl implements ITimeSheetController {

    @Autowired
    private TimeSheetService timeSheetService;

    /**
     * Create new timesheet
     */
    @Override
    @PostMapping("/create")
    public ResponseEntity<TimeSheetResponse> createTimeSheet(@Valid @RequestBody TimeSheetRequest requestDto) {
        TimeSheetResponse response = timeSheetService.createTimeSheet(requestDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Update timesheet
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<TimeSheetResponse> updateTimeSheet(
            @PathVariable UUID id,
            @Valid @RequestBody TimeSheetRequest requestDto) {

        TimeSheetResponse response = timeSheetService.updateTimeSheet(id, requestDto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/submit/{id}")
    public ResponseEntity<TimeSheetResponse> updateTimeSheetStatus(
            @PathVariable UUID id,
            @RequestBody Map<String, String> request) {

        String newStatus = request.get("status"); // expecting { "status": "SUBMITTED" }
        TimeSheetResponse response = timeSheetService.updateStatus(id, newStatus);
        return ResponseEntity.ok(response);
    }

    /**
     * Get all timesheets for a specific date
     */
    // @GetMapping("/date/{employeeId}/{date}")
    // public ResponseEntity<List<TimeSheetResponse>> getTimeSheetsByDate(
    // @PathVariable UUID employeeId,
    // @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date)
    // {

    // List<TimeSheetResponse> responses =
    // timeSheetService.getTimeSheetsByDate(employeeId, date);
    // return ResponseEntity.ok(responses);
    // }

    /**
     * Get all timesheets for a specific month
     */
    // @GetMapping("/{employeeId}/{year}/{month}")
    // public ResponseEntity<List<TimeSheetResponse>> getTimeSheetsByMonth(
    // @PathVariable UUID employeeId,
    // @PathVariable int year,
    // @PathVariable int month) {

    // List<TimeSheetResponse> responses =
    // timeSheetService.getTimeSheetsByMonth(employeeId, year, month);
    // return ResponseEntity.ok(responses);
    // }

    /**
     * Delete a timesheet
     */
    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTimeSheet(@PathVariable UUID id) {
        timeSheetService.deleteTimeSheet(id);
        return ResponseEntity.ok("Timesheet deleted successfully.");
    }

    /**
     * Get calendar status view for employee
     */
    @GetMapping("/status_calendar/{employeeId}/{year}/{month}")
    public ResponseEntity<List<CalendarStatusResponse>> getCalendarStatus(
            @PathVariable UUID employeeId,
            @PathVariable int year,
            @PathVariable int month) {

        List<CalendarStatusResponse> responses = timeSheetService.getCalendarStatus(employeeId, year, month);
        return ResponseEntity.ok(responses);
    }

    /**
     * Get or create a timesheet for a date
     * When user clicks on date, it will return existing timesheet or create a new
     * one
     */
    @PostMapping("/get-or-create/{employeeId}/{date}")
    public ResponseEntity<TimeSheetResponse> getOrCreateTimeSheet(
            @PathVariable UUID employeeId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        TimeSheetResponse response = timeSheetService.getOrCreateTimeSheet(employeeId, date);
        return ResponseEntity.ok(response);
    }
}
