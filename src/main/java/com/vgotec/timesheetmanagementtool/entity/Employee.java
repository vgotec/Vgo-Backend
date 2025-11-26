package com.vgotec.timesheetmanagementtool.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.*;

@Entity
@Table(name = "employee", uniqueConstraints = {
        @UniqueConstraint(name = "employee_employee_code_key", columnNames = "employee_code")
})
public class Employee {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "uuid DEFAULT gen_random_uuid()", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "employee_code", unique = true, length = 50)
    private String employeeCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "employee_user_id_fkey"))
    private User user;

    private String email;
    private String designation;
    private String department;
    private String division;
    private LocalDate joiningDate;
    @Column(name = "role", length = 50)
    private String role; // EMPLOYEE, MANAGER, HR

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporting_to", foreignKey = @ForeignKey(name = "fk_employee_reporting_to"))
    private Employee reportingTo;

    private String status = "ACTIVE";

    private OffsetDateTime createdAt = OffsetDateTime.now();
    private OffsetDateTime updatedAt = OffsetDateTime.now();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<EmployeeEducation> educationList = new ArrayList<>();

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private EmployeePersonal personal;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private EmployeeProfessional professional;

    // ---------- Lifecycle Hooks ----------
    @PrePersist
    public void onCreate() {
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = OffsetDateTime.now();
    }

    // ---------- Relationship Helpers ----------
    public void addEducation(EmployeeEducation education) {
        if (education != null) {
            education.setEmployee(this);
            this.educationList.add(education);
        }
    }

    public void setPersonal(EmployeePersonal personal) {
        if (personal != null) {
            personal.setEmployee(this);
        }
        this.personal = personal;
    }

    public void setProfessional(EmployeeProfessional professional) {
        if (professional != null) {
            professional.setEmployee(this);
        }
        this.professional = professional;
    }

    // ---------- Getters and Setters ----------
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Employee getReportingTo() {
        return reportingTo;
    }

    public void setReportingTo(Employee reportingTo) {
        this.reportingTo = reportingTo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<EmployeeEducation> getEducationList() {
        return educationList;
    }

    public void setEducationList(List<EmployeeEducation> educationList) {
        this.educationList = educationList;
    }

    public EmployeePersonal getPersonal() {
        return personal;
    }

    public EmployeeProfessional getProfessional() {
        return professional;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
