package com.vgotec.timesheetmanagementtool.validation;

import com.vgotec.timesheetmanagementtool.entity.Leave;
import com.vgotec.timesheetmanagementtool.repository.LeaveRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
public class LeaveValidationService {

    private final LeaveRepository leaveRepository;

    public LeaveValidationService(LeaveRepository leaveRepository) {
        this.leaveRepository = leaveRepository;
    }

    /**
     * 💡 Validate overlap + valid range + no past dates.
     * Used for both create & update.
     */
    public void validateNoLeaveOverlap(UUID employeeId,
            LocalDate newStart,
            LocalDate newEnd,
            UUID excludeLeaveId) {

        // Basic validations
        validateNoPastDates(newStart, newEnd);
        validateDateRange(newStart, newEnd);

        // Query overlapping leaves
        List<Leave> overlappingLeaves = leaveRepository
                .findByEmployeeIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                        employeeId,
                        newEnd,
                        newStart);

        for (Leave existingLeave : overlappingLeaves) {

            // Skip SAME leave during update
            if (excludeLeaveId != null &&
                    existingLeave.getId().equals(excludeLeaveId)) {
                continue;
            }

            // Final overlap validation (defensive)
            if (!newStart.isAfter(existingLeave.getEndDate()) &&
                    !newEnd.isBefore(existingLeave.getStartDate())) {

                throw new RuntimeException(
                        "Leave dates overlap with an existing leave from " +
                                existingLeave.getStartDate() + " to " +
                                existingLeave.getEndDate());
            }
        }
    }

    /**
     * Overloaded method for CREATE scenario
     * (no ID to exclude)
     */
    public void validateNoLeaveOverlap(UUID employeeId,
            LocalDate startDate,
            LocalDate endDate) {
        validateNoLeaveOverlap(employeeId, startDate, endDate, null);
    }

    /**
     * Validate: startDate <= endDate
     */
    public void validateDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new RuntimeException("Start date and end date are required.");
        }

        if (startDate.isAfter(endDate)) {
            throw new RuntimeException(
                    "Invalid date range: start date cannot be after end date.");
        }
    }

    /**
     * Validate: dates should not be in past
     */
    public void validateNoPastDates(LocalDate startDate, LocalDate endDate) {
        LocalDate today = LocalDate.now();

        if (startDate == null || endDate == null) {
            throw new RuntimeException("Start date and end date are required.");
        }

        if (startDate.isBefore(today)) {
            throw new RuntimeException("Start date cannot be in the past.");
        }

        if (endDate.isBefore(today)) {
            throw new RuntimeException("End date cannot be in the past.");
        }
    }
}
