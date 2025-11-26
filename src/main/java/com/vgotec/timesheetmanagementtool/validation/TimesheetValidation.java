package com.vgotec.timesheetmanagementtool.validation;

import com.vgotec.timesheetmanagementtool.entity.TimesheetEntry;
import com.vgotec.timesheetmanagementtool.repository.EmployeeRepository;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public class TimesheetValidation {

    public static void validateTimeRange(LocalTime startTime, LocalTime endTime) {

        if (startTime == null || endTime == null) {
            throw new IllegalArgumentException("Start Time and End Time cannot be null");
        }

        if (!startTime.isBefore(endTime)) {
            throw new IllegalArgumentException("Start Time must be earlier than End Time");
        }

        Duration duration = Duration.between(startTime, endTime);
        double hours = duration.toMinutes() / 60.0;

        if (hours > 8) {
            throw new IllegalArgumentException(
                    "Each activity cannot exceed 8 hours. Entered: " + hours + " hours");
        }
    }

    public static void validateOverlap(LocalTime startTime, LocalTime endTime, List<TimesheetEntry> existing) {

        for (TimesheetEntry e : existing) {

            boolean overlaps = startTime.isBefore(e.getEndTime()) &&
                    endTime.isAfter(e.getStartTime());

            if (overlaps) {
                throw new IllegalArgumentException(
                        "Time overlaps with existing activity: " +
                                e.getStartTime() + " to " + e.getEndTime());
            }
        }
    }

    public static void validateTotalHours(
            UUID currentEntryId,
            double newActivityHours,
            List<TimesheetEntry> allEntries) {

        double existingHours = allEntries.stream()
                .filter(e -> currentEntryId == null || !e.getId().equals(currentEntryId))
                .mapToDouble(TimesheetEntry::getDurationHours)
                .sum();

        double totalAfterAdding = existingHours + newActivityHours;

        if (totalAfterAdding > 8) {
            throw new IllegalArgumentException(
                    "Total daily hours cannot exceed 8 hours. Existing: "
                            + existingHours + " hrs, adding: " + newActivityHours +
                            " hrs → total: " + totalAfterAdding + " hrs");
        }
    }

    public static void validateEmployeeId(UUID employeeId) {
        if (employeeId == null) {
            throw new IllegalArgumentException("Employee ID cannot be null");
        }
    }

    public static void validateEmployeeExists(UUID employeeId, EmployeeRepository repo) {
        if (!repo.existsById(employeeId)) {
            throw new IllegalArgumentException("Invalid employee ID: employee not found");
        }
    }

}
