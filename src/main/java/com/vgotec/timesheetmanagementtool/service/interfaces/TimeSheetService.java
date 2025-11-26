package com.vgotec.timesheetmanagementtool.service.interfaces;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.vgotec.timesheetmanagementtool.dto.ActivityRequest;
import com.vgotec.timesheetmanagementtool.dto.CalendarStatusResponse;
import com.vgotec.timesheetmanagementtool.dto.TimeSheetRequest;
import com.vgotec.timesheetmanagementtool.dto.TimeSheetResponse;

public interface TimeSheetService {

    /**
     * Creates a new timesheet entry for the given employee.
     *
     * @param requestDto the DTO containing timesheet input data
     * @return the saved timesheet details as a response DTO
     */
    TimeSheetResponse createTimeSheet(TimeSheetRequest requestDto);

    /**
     * Updates an existing timesheet entry.
     *
     * @param id         the UUID of the timesheet to update
     * @param requestDto the DTO containing updated timesheet data
     * @return the updated timesheet details as a response DTO
     */
    TimeSheetResponse updateTimeSheet(UUID id, TimeSheetRequest requestDto);

    /**
     * Retrieves all timesheets for a specific employee on a given date.
     *
     * @param employeeId the UUID of the employee
     * @param date       the date to filter timesheets
     * @return list of matching timesheets
     */
    // List<TimeSheetResponse> getTimeSheetsByDate(UUID employeeId, LocalDate date);

    /**
     * Retrieves all timesheets for a specific employee in a given month and year.
     *
     * @param employeeId the UUID of the employee
     * @param year       the year to filter
     * @param month      the month to filter (1–12)
     * @return list of timesheets
     */
    // List<TimeSheetResponse> getTimeSheetsByMonth(UUID employeeId, int year, int
    // month);

    /**
     * Deletes a timesheet by its UUID.
     *
     * @param id the UUID of the timesheet to delete
     */
    void deleteTimeSheet(UUID id);

    /**
     * Returns a calendar view of timesheet statuses for the given month and year.
     *
     * @param employeeId the UUID of the employee
     * @param year       the year to filter
     * @param month      the month to filter
     * @return list of calendar status responses
     */
    List<CalendarStatusResponse> getCalendarStatus(UUID employeeId, int year, int month);

    /**
     * Retrieves an existing timesheet for the given date, or creates a new one if
     * none exists.
     *
     * @param employeeId the UUID of the employee
     * @param date       the date to check or create timesheet
     * @return the existing or newly created timesheet
     */
    TimeSheetResponse getOrCreateTimeSheet(UUID employeeId, LocalDate date);

    /**
     * Adds a new activity entry to an existing timesheet and updates total hours.
     *
     * @param timesheetId the UUID of the timesheet
     * @param request     the activity request containing details
     * @return the updated timesheet response with all activities
     */
    TimeSheetResponse updateStatus(UUID id, String newStatus);
}
