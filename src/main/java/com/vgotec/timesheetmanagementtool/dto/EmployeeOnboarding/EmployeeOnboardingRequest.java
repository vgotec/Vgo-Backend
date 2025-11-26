package com.vgotec.timesheetmanagementtool.dto.EmployeeOnboarding;

import java.time.LocalDate;
import java.util.List;

public class EmployeeOnboardingRequest {

    private String employeeCode;
    private String email;
    private String designation;
    private String department;
    private String division;
    private LocalDate joiningDate;
    private String reportingToId;
    private String role; // EMPLOYEE, MANAGER, HR

    private EmployeePersonalDto personal;
    private EmployeeProfessionalDto professional;
    private List<EmployeeEducationDto> educationList;

    // ---------------- Getters & Setters ----------------

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getReportingToId() {
        return reportingToId;
    }

    public void setReportingToId(String reportingToId) {
        this.reportingToId = reportingToId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public EmployeePersonalDto getPersonal() {
        return personal;
    }

    public void setPersonal(EmployeePersonalDto personal) {
        this.personal = personal;
    }

    public EmployeeProfessionalDto getProfessional() {
        return professional;
    }

    public void setProfessional(EmployeeProfessionalDto professional) {
        this.professional = professional;
    }

    public List<EmployeeEducationDto> getEducationList() {
        return educationList;
    }

    public void setEducationList(List<EmployeeEducationDto> educationList) {
        this.educationList = educationList;
    }
}
