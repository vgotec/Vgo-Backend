package com.vgotec.timesheetmanagementtool.validation;

import com.vgotec.timesheetmanagementtool.dto.ActivityRequest;
//import com.vgotec.timesheetmanagementtool.entity.TimesheetEntry;

import java.time.LocalTime;
import java.time.OffsetDateTime;
//import java.util.List;

public class ActivityValidation {

    public static void validateRequest(ActivityRequest request) {
        if (request == null)
            throw new IllegalArgumentException("Request cannot be null");

        if (isEmpty(request.getProject()))
            throw new IllegalArgumentException("Project is required");

        if (isEmpty(request.getActivity()))
            throw new IllegalArgumentException("Activity is required");

        if (isEmpty(request.getCategory()))
            throw new IllegalArgumentException("Category is required");

        if (request.getStartTime() == null)
            throw new IllegalArgumentException("Start time is required");

        if (request.getEndTime() == null)
            throw new IllegalArgumentException("End time is required");

        if (request.getWorkHours() == null || request.getWorkHours() <= 0)
            throw new IllegalArgumentException("Work hours must be greater than 0");
    }

    public static void validateDurationMatching(Double workHours,
            LocalTime start,
            LocalTime end) {

        double actual = (end.toSecondOfDay() - start.toSecondOfDay()) / 3600.0;

        if (actual <= 0) {
            throw new IllegalArgumentException("End time must be after start time");
        }

        if (Math.abs(actual - workHours) > 0.01) {
            throw new IllegalArgumentException("Work hours do not match the time duration provided");
        }
    }

    private static boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }
}
