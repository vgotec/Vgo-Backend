package com.vgotec.timesheetmanagementtool.service.impl;

import com.vgotec.timesheetmanagementtool.dto.CalendarLeaveStatusResponse;
import com.vgotec.timesheetmanagementtool.dto.EmployeeLeaveBalanceResponse;
import com.vgotec.timesheetmanagementtool.dto.LeaveRequest;
import com.vgotec.timesheetmanagementtool.dto.LeaveResponse;
import com.vgotec.timesheetmanagementtool.entity.EmployeeLeaveBalance;
import com.vgotec.timesheetmanagementtool.entity.Leave;
import com.vgotec.timesheetmanagementtool.entity.LeaveStatusEntity;
import com.vgotec.timesheetmanagementtool.entity.LeaveType;
import com.vgotec.timesheetmanagementtool.enums.LeaveStatus;
import com.vgotec.timesheetmanagementtool.repository.EmployeeLeaveBalanceRepository;
import com.vgotec.timesheetmanagementtool.repository.LeaveRepository;
import com.vgotec.timesheetmanagementtool.repository.LeaveStatusRepository;
import com.vgotec.timesheetmanagementtool.repository.LeaveTypeRepository;
import com.vgotec.timesheetmanagementtool.service.interfaces.LeaveService;
import com.vgotec.timesheetmanagementtool.validation.LeaveValidationService;
import com.vgotec.timesheetmanagementtool.util.LeaveFormUtil;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LeaveServiceImpl implements LeaveService {

    private final LeaveRepository leaveRepository;
    private final LeaveTypeRepository leaveTypeRepository;
    private final EmployeeLeaveBalanceRepository employeeLeaveBalanceRepository;
    private final LeaveStatusRepository leaveStatusRepository;
    private final LeaveValidationService leaveValidationService;

    public LeaveServiceImpl(
            LeaveRepository leaveRepository,
            LeaveTypeRepository leaveTypeRepository,
            EmployeeLeaveBalanceRepository employeeLeaveBalanceRepository,
            LeaveStatusRepository leaveStatusRepository,
            LeaveValidationService leaveValidationService) {

        this.leaveRepository = leaveRepository;
        this.leaveTypeRepository = leaveTypeRepository;
        this.employeeLeaveBalanceRepository = employeeLeaveBalanceRepository;
        this.leaveStatusRepository = leaveStatusRepository;
        this.leaveValidationService = leaveValidationService;
    }

    // --------------------------------------------------------
    // CREATE LEAVE (Always SUBMITTED)
    // --------------------------------------------------------
    @Override
    public LeaveResponse submitLeave(UUID employeeId, LeaveRequest dto) {
        return createLeave(employeeId, dto);
    }

    private LeaveResponse createLeave(UUID employeeId, LeaveRequest dto) {

        leaveValidationService.validateNoLeaveOverlap(employeeId, dto.getStartDate(), dto.getEndDate());

        LeaveType leaveType = getLeaveType(dto.getLeaveTypeName());
        double totalDays = calculateLeaveDays(dto.getStartDate(), dto.getEndDate(), dto.getSession());

        Leave leave = new Leave();
        leave.setEmployeeId(employeeId);
        leave.setLeaveTypeId(leaveType.getId());
        leave.setStartDate(dto.getStartDate());
        leave.setEndDate(dto.getEndDate());
        leave.setSession(Optional.ofNullable(dto.getSession()).orElse("FULL_DAY"));
        leave.setRemarks(dto.getRemarks());
        leave.setStatus(LeaveStatus.SUBMITTED);
        leave.setCreatedAt(LocalDateTime.now());
        leave.setUpdatedAt(LocalDateTime.now());

        leaveRepository.save(leave);

        updateEmployeeLeaveBalance(employeeId, leaveType.getId(), totalDays);

        LeaveResponse response = new LeaveResponse(
                leave.getId(),
                leaveType.getName(),
                leave.getStartDate(),
                leave.getEndDate(),
                leave.getSession(),
                leave.getRemarks(),
                leave.getStatus(),
                totalDays,
                "Leave submitted successfully");

        response.setLeaveBalance(getEmployeeLeaveBalances(employeeId));
        return response;
    }

    // --------------------------------------------------------
    // UPDATE Leave
    // --------------------------------------------------------
    @Override
    public LeaveResponse updateExistingLeave(UUID leaveId, LeaveRequest dto) {

        Leave leave = getLeaveById(leaveId);
        UUID employeeId = leave.getEmployeeId();

        leaveValidationService.validateNoLeaveOverlap(
                employeeId, dto.getStartDate(), dto.getEndDate(), leaveId);

        LeaveType oldType = leaveTypeRepository.findById(leave.getLeaveTypeId())
                .orElseThrow(() -> new RuntimeException("Invalid leave type"));
        double oldDays = calculateLeaveDays(leave.getStartDate(), leave.getEndDate(), leave.getSession());

        LeaveType newType = getLeaveType(dto.getLeaveTypeName());
        double newDays = calculateLeaveDays(dto.getStartDate(), dto.getEndDate(), dto.getSession());

        updateEmployeeLeaveBalance(employeeId, leave.getLeaveTypeId(), -oldDays);
        updateEmployeeLeaveBalance(employeeId, newType.getId(), newDays);

        leave.setLeaveTypeId(newType.getId());
        leave.setStartDate(dto.getStartDate());
        leave.setEndDate(dto.getEndDate());
        leave.setSession(Optional.ofNullable(dto.getSession()).orElse("FULL_DAY"));
        leave.setRemarks(dto.getRemarks());
        leave.setUpdatedAt(LocalDateTime.now());

        leaveRepository.save(leave);

        return new LeaveResponse(
                leave.getId(),
                newType.getName(),
                leave.getStartDate(),
                leave.getEndDate(),
                leave.getSession(),
                leave.getRemarks(),
                leave.getStatus(),
                newDays,
                "Leave updated successfully");
    }

    // --------------------------------------------------------
    // DELETE
    // --------------------------------------------------------
    @Override
    public String deleteLeave(UUID leaveId) {
        Leave leave = getLeaveById(leaveId);
        leaveRepository.delete(leave);
        return "Leave deleted successfully";
    }

    // --------------------------------------------------------
    // GET BALANCE
    // --------------------------------------------------------
    @Override
    public List<EmployeeLeaveBalanceResponse> getEmployeeLeaveBalances(UUID employeeId) {

        int year = LocalDate.now().getYear();
        List<LeaveType> leaveTypes = leaveTypeRepository.findAll();

        Map<UUID, EmployeeLeaveBalance> balanceMap = employeeLeaveBalanceRepository
                .findByEmployeeIdAndYear(employeeId, year)
                .stream()
                .collect(Collectors.toMap(EmployeeLeaveBalance::getLeaveTypeId, b -> b));

        return leaveTypes.stream()
                .map(type -> {
                    EmployeeLeaveBalance bal = balanceMap.get(type.getId());
                    return new EmployeeLeaveBalanceResponse(
                            type.getId(),
                            type.getName(),
                            year,
                            bal != null ? bal.getUsedDays() : 0,
                            bal != null ? bal.getRemainingDays() : type.getYearlyLimit(),
                            bal != null ? bal.getUpdatedAt() : LocalDateTime.now());
                }).toList();
    }

    // --------------------------------------------------------
    // GET LEAVE ON SPECIFIC DATE
    // --------------------------------------------------------
    @Override
    public LeaveResponse getLeaveOnDate(UUID employeeId, LocalDate date) {

        List<Leave> leaves = leaveRepository
                .findByEmployeeIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(employeeId, date, date);

        LeaveResponse response = new LeaveResponse();
        response.setLeaveBalance(getEmployeeLeaveBalances(employeeId));

        // CASE 1: NO LEAVE → send create_leave_form
        if (leaves.isEmpty()) {

            Map<String, Object> form = LeaveFormUtil.loadForm("forms/create_leave_form.json");
            LeaveFormUtil.injectLeaveTypeDropdown(
                    form,
                    leaveTypeRepository.findAll());

            response.setFormJson(form);
            response.setLeave(null);
            response.setMessage("No leave found for the selected date");
            return response;
        }

        // CASE 2: LEAVE EXISTS → modify form
        Leave leave = leaves.get(0);

        LeaveType type = leaveTypeRepository.findById(leave.getLeaveTypeId())
                .orElseThrow(() -> new RuntimeException("Invalid leave type"));

        Map<String, Object> form = LeaveFormUtil.loadForm("forms/modify_leave_form.json");
        LeaveFormUtil.injectExistingLeaveValues(form, leave, type.getName());
        LeaveFormUtil.injectLeaveId(form, leave.getId());

        LeaveFormUtil.injectLeaveTypeDropdown(
                form,
                leaveTypeRepository.findAll());
        response.setFormJson(form);

        double totalDays = calculateLeaveDays(
                leave.getStartDate(), leave.getEndDate(), leave.getSession());

        response.setLeave(buildLeaveJson(leave, type.getName(), totalDays));
        response.setMessage("Leave found for the selected date");

        return response;
    }

    // --------------------------------------------------------
    // CALENDAR STATUS
    // --------------------------------------------------------
    @Override
    public List<CalendarLeaveStatusResponse> getMonthlyLeaveStatus(UUID employeeId, int year, int month) {

        List<Leave> leaves = filterLeavesForMonth(employeeId, year, month);
        Map<String, String> colorMap = getStatusColorMap();

        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        List<CalendarLeaveStatusResponse> result = new ArrayList<>();

        LocalDate d = start;
        while (!d.isAfter(end)) {
            Optional<Leave> found = findLeaveCoveringDate(leaves, d);
            if (found.isPresent()) {
                String status = found.get().getStatus().name();
                result.add(new CalendarLeaveStatusResponse(
                        d, status, colorMap.getOrDefault(status, "#9E9E9E")));
            } else {
                result.add(new CalendarLeaveStatusResponse(
                        d, "NONE", colorMap.getOrDefault("NONE", "#E0E0E0")));
            }
            d = d.plusDays(1);
        }

        return result;
    }

    // --------------------------------------------------------
    // PRIVATE HELPERS
    // --------------------------------------------------------
    private Leave getLeaveById(UUID leaveId) {
        return leaveRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave not found for ID: " + leaveId));
    }

    private LeaveType getLeaveType(String name) {
        return leaveTypeRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new RuntimeException("Invalid leave type: " + name));
    }

    private Map<String, Object> buildLeaveJson(Leave leave, String leaveTypeName, double totalDays) {
        Map<String, Object> json = new LinkedHashMap<>();
        json.put("id", leave.getId());
        json.put("leaveTypeName", leaveTypeName);
        json.put("startDate", leave.getStartDate());
        json.put("endDate", leave.getEndDate());
        json.put("session", leave.getSession());
        json.put("remarks", leave.getRemarks());
        json.put("status", leave.getStatus());
        json.put("totalDays", totalDays);
        return json;
    }

    private Optional<Leave> findLeaveCoveringDate(List<Leave> leaves, LocalDate date) {
        return leaves.stream()
                .filter(l -> !date.isBefore(l.getStartDate()) && !date.isAfter(l.getEndDate()))
                .findFirst();
    }

    private List<Leave> filterLeavesForMonth(UUID employeeId, int year, int month) {
        return leaveRepository.findByEmployeeId(employeeId)
                .stream()
                .filter(l -> (l.getStartDate().getYear() == year || l.getEndDate().getYear() == year) &&
                        (l.getStartDate().getMonthValue() == month || l.getEndDate().getMonthValue() == month))
                .toList();
    }

    private Map<String, String> getStatusColorMap() {
        return leaveStatusRepository.findAll()
                .stream()
                .collect(Collectors.toMap(
                        s -> s.getName().toUpperCase(),
                        LeaveStatusEntity::getColorCode,
                        (a, b) -> a));
    }

    private double calculateLeaveDays(LocalDate start, LocalDate end, String session) {
        long totalDays = ChronoUnit.DAYS.between(start, end) + 1;

        if (totalDays == 1) {
            if (session == null)
                return 1.0;

            return (session.equalsIgnoreCase("FIRST_HALF")
                    || session.equalsIgnoreCase("SECOND_HALF"))
                            ? 0.5
                            : 1.0;
        }

        double days = totalDays;

        if (session != null) {
            if (session.equalsIgnoreCase("FIRST_HALF"))
                days -= 0.5;
            if (session.equalsIgnoreCase("SECOND_HALF"))
                days -= 0.5;
        }

        return days;
    }

    private void updateEmployeeLeaveBalance(UUID employeeId, UUID leaveTypeId, double usedDays) {

        int year = LocalDate.now().getYear();

        EmployeeLeaveBalance bal = employeeLeaveBalanceRepository
                .findByEmployeeIdAndLeaveTypeIdAndYear(employeeId, leaveTypeId, year)
                .orElseGet(() -> {
                    LeaveType type = leaveTypeRepository.findById(leaveTypeId)
                            .orElseThrow(() -> new RuntimeException("Invalid leave type ID"));
                    EmployeeLeaveBalance b = new EmployeeLeaveBalance();
                    b.setEmployeeId(employeeId);
                    b.setLeaveTypeId(leaveTypeId);
                    b.setYear(year);
                    b.setUsedDays(0);
                    b.setRemainingDays(type.getYearlyLimit());
                    return b;
                });

        bal.setUsedDays(bal.getUsedDays() + usedDays);
        bal.setRemainingDays(Math.max(bal.getRemainingDays() - usedDays, 0));
        bal.setUpdatedAt(LocalDateTime.now());

        employeeLeaveBalanceRepository.save(bal);
    }
}
