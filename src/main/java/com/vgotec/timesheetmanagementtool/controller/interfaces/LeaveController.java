package com.vgotec.timesheetmanagementtool.controller.interfaces;

import com.vgotec.timesheetmanagementtool.dto.LeaveRequest;
import com.vgotec.timesheetmanagementtool.dto.LeaveResponse;
import com.vgotec.timesheetmanagementtool.dto.CalendarLeaveStatusResponse;
import com.vgotec.timesheetmanagementtool.dto.EmployeeLeaveBalanceResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/leaves")
public interface LeaveController {

        /**
         * 📤 Submit leave (EMPLOYEE ID must come from PathVariable)
         */
        @PostMapping("/{employeeId}/submit")
        ResponseEntity<LeaveResponse> submitLeave(
                        @PathVariable UUID employeeId,
                        @RequestBody LeaveRequest dto);

        /**
         * ✏️ Modify an existing leave (no employeeId in path; read from DB)
         */
        @PutMapping("/modify/{leaveId}")
        ResponseEntity<LeaveResponse> modifyLeave(
                        @PathVariable UUID leaveId,
                        @RequestBody LeaveRequest request);

        /**
         * ❌ Delete an existing leave (no employeeId needed)
         */
        @DeleteMapping("/delete/{leaveId}")
        ResponseEntity<String> deleteLeave(@PathVariable UUID leaveId);

        /**
         * 📊 Get leave balance
         */
        @GetMapping("/balance/{employeeId}")
        ResponseEntity<List<EmployeeLeaveBalanceResponse>> getLeaveBalance(
                        @PathVariable UUID employeeId);

        /**
         * 🗓 Get monthly leave status
         */
        @GetMapping("/{employeeId}/calendar/{year}/{month}")
        ResponseEntity<List<CalendarLeaveStatusResponse>> getMonthlyLeaveStatus(
                        @PathVariable UUID employeeId,
                        @PathVariable int year,
                        @PathVariable int month);

        /**
         * 🔍 Get leave on a specific date
         */
        @GetMapping("/leave/by-date/{employeeId}/{date}")
        ResponseEntity<LeaveResponse> getLeaveOnDate(
                        @PathVariable UUID employeeId,
                        @PathVariable String date);
}
