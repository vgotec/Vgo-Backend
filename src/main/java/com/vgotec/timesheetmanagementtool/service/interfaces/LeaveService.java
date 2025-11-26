package com.vgotec.timesheetmanagementtool.service.interfaces;

import com.vgotec.timesheetmanagementtool.dto.EmployeeLeaveBalanceResponse;
import com.vgotec.timesheetmanagementtool.dto.LeaveRequest;
import com.vgotec.timesheetmanagementtool.dto.LeaveResponse;
import com.vgotec.timesheetmanagementtool.dto.CalendarLeaveStatusResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface LeaveService {

    /**
     * 🔹 Create leave (always SUBMITTED)
     */
    LeaveResponse submitLeave(UUID empId, LeaveRequest dto);

    /**
     * ✏️ Update an existing leave
     */
    LeaveResponse updateExistingLeave(UUID leaveId, LeaveRequest dto);

    /**
     * ❌ Delete an existing leave
     */
    String deleteLeave(UUID leaveId);

    /**
     * 📊 Get employee’s yearly leave balances per leave type
     */
    List<EmployeeLeaveBalanceResponse> getEmployeeLeaveBalances(UUID employeeId);

    /**
     * 🗓️ Check if employee has leave on a particular date
     */
    LeaveResponse getLeaveOnDate(UUID employeeId, LocalDate date);

    /**
     * 🗓️ Get calendar leave status for the entire month
     */
    List<CalendarLeaveStatusResponse> getMonthlyLeaveStatus(UUID employeeId, int year, int month);
}
