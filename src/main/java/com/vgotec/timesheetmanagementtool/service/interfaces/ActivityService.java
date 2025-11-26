package com.vgotec.timesheetmanagementtool.service.interfaces;

import com.vgotec.timesheetmanagementtool.dto.ActivityRequest;
import com.vgotec.timesheetmanagementtool.dto.TimeSheetEntryResponse;

import java.time.LocalDate;
import java.util.UUID;

public interface ActivityService {

    TimeSheetEntryResponse createActivity(ActivityRequest request, UUID timesheetId);

    TimeSheetEntryResponse updateActivity(UUID entryId, ActivityRequest request);

    void deleteActivity(UUID entryId);

    TimeSheetEntryResponse createActivityByDate(ActivityRequest request, UUID employeeId, LocalDate date);

}
