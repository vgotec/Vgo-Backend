package com.vgotec.timesheetmanagementtool.controller.impl;

import com.vgotec.timesheetmanagementtool.controller.interfaces.LeaveController;
import com.vgotec.timesheetmanagementtool.dto.CalendarLeaveStatusResponse;
import com.vgotec.timesheetmanagementtool.dto.EmployeeLeaveBalanceResponse;
import com.vgotec.timesheetmanagementtool.dto.LeaveRequest;
import com.vgotec.timesheetmanagementtool.dto.LeaveResponse;
//import com.vgotec.timesheetmanagementtool.entity.EmployeeLeaveBalance;
import com.vgotec.timesheetmanagementtool.service.interfaces.LeaveService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.time.LocalDate;
import java.util.List;

@RestController
public class LeaveControllerImpl implements LeaveController {

    private final LeaveService leaveService;

    public LeaveControllerImpl(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    @PostMapping("/submit/{employeeId}")
    public ResponseEntity<LeaveResponse> submitLeave(
            @PathVariable UUID employeeId,
            @RequestBody LeaveRequest dto) {

        LeaveResponse response = leaveService.submitLeave(employeeId, dto);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<LeaveResponse> modifyLeave(UUID leaveId, LeaveRequest request) {
        LeaveResponse response = leaveService.updateExistingLeave(leaveId, request);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<String> deleteLeave(UUID leaveId) {
        String message = leaveService.deleteLeave(leaveId);
        return ResponseEntity.ok(message);
    }

    @Override
    public ResponseEntity<List<EmployeeLeaveBalanceResponse>> getLeaveBalance(UUID employeeId) {
        List<EmployeeLeaveBalanceResponse> balanceList = leaveService.getEmployeeLeaveBalances(employeeId);
        return ResponseEntity.ok(balanceList);
    }

    @Override
    public ResponseEntity<List<CalendarLeaveStatusResponse>> getMonthlyLeaveStatus(
            UUID employeeId, int year, int month) {

        List<CalendarLeaveStatusResponse> response = leaveService.getMonthlyLeaveStatus(employeeId, year, month);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<LeaveResponse> getLeaveOnDate(UUID employeeId, String date) {

        LocalDate selectedDate = LocalDate.parse(date);

        LeaveResponse response = leaveService.getLeaveOnDate(employeeId, selectedDate);

        return ResponseEntity.ok(response); // null allowed
    }
}
