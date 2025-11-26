package com.vgotec.timesheetmanagementtool.dto.EmployeeOnboarding;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class EmployeeDetailDto {

    private UUID employeeId;
    private String employeeCode;
    private String fullName;

    private String email;
    private String designation;
    private String department;
    private String division;
    private LocalDate joiningDate;
    private String role;
    private String reportingToId;

    private EmployeePersonalDto personal;
    private EmployeeProfessionalDto professional;
    private List<EmployeeEducationDto> educationList;

    public EmployeeDetailDto(UUID employeeId, String employeeCode, String fullName,
            String email, String designation, String department,
            String division, LocalDate joiningDate, String role,
            String reportingToId, EmployeePersonalDto personal,
            EmployeeProfessionalDto professional,
            List<EmployeeEducationDto> educationList) {

        this.employeeId = employeeId;
        this.employeeCode = employeeCode;
        this.fullName = fullName;
        this.email = email;
        this.designation = designation;
        this.department = department;
        this.division = division;
        this.joiningDate = joiningDate;
        this.role = role;
        this.reportingToId = reportingToId;
        this.personal = personal;
        this.professional = professional;
        this.educationList = educationList;
    }

    public UUID getEmployeeId() {
        return employeeId;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getDesignation() {
        return designation;
    }

    public String getDepartment() {
        return department;
    }

    public String getDivision() {
        return division;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public String getRole() {
        return role;
    }

    public String getReportingToId() {
        return reportingToId;
    }

    public EmployeePersonalDto getPersonal() {
        return personal;
    }

    public EmployeeProfessionalDto getProfessional() {
        return professional;
    }

    public List<EmployeeEducationDto> getEducationList() {
        return educationList;
    }
}
