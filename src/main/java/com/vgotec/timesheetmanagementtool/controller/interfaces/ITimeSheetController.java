package com.vgotec.timesheetmanagementtool.controller.interfaces;

import com.vgotec.timesheetmanagementtool.dto.TimeSheetRequest;
import com.vgotec.timesheetmanagementtool.dto.TimeSheetResponse;
import com.vgotec.timesheetmanagementtool.dto.CalendarStatusResponse;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ITimeSheetController {

        ResponseEntity<TimeSheetResponse> createTimeSheet(@Valid @RequestBody TimeSheetRequest requestDto);

        ResponseEntity<TimeSheetResponse> updateTimeSheet(
                        @PathVariable UUID id,
                        @Valid @RequestBody TimeSheetRequest requestDto);

        // ResponseEntity<List<TimeSheetResponse>> getTimeSheetsByDate(
        // @PathVariable UUID employeeId,
        // @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date);

        // ResponseEntity<List<TimeSheetResponse>> getTimeSheetsByMonth(
        // @PathVariable UUID employeeId,
        // @PathVariable int year,
        // @PathVariable int month);

        ResponseEntity<String> deleteTimeSheet(@PathVariable UUID id);

        ResponseEntity<TimeSheetResponse> getOrCreateTimeSheet(
                        @PathVariable UUID employeeId,
                        @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date);

        ResponseEntity<List<CalendarStatusResponse>> getCalendarStatus(
                        @PathVariable UUID employeeId,
                        @PathVariable int year,
                        @PathVariable int month);

}
