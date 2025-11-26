package com.vgotec.timesheetmanagementtool.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "employee_education")
public class EmployeeEducation {

    /** Primary key: UUID */
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "uuid DEFAULT gen_random_uuid()", updatable = false, nullable = false)
    private UUID id;

    /** FK → employee(id) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false, foreignKey = @ForeignKey(name = "employee_education_employee_id_fkey"))
    private Employee employee;

    @Column(name = "highest_qualification", length = 150)
    private String highestQualification;

    @Column(name = "institute_name", length = 255)
    private String instituteName;

    @Column(name = "degree_diploma", length = 255)
    private String degreeDiploma;

    @Column(name = "completion_date")
    private LocalDate completionDate;

    @Column(name = "created_at")
    private OffsetDateTime createdAt = OffsetDateTime.now();

    // ---------- Lifecycle Hooks ----------
    @PrePersist
    public void onCreate() {
        this.createdAt = OffsetDateTime.now();
    }

    // ---------- Getters and Setters ----------
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getHighestQualification() {
        return highestQualification;
    }

    public void setHighestQualification(String highestQualification) {
        this.highestQualification = highestQualification;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public String getDegreeDiploma() {
        return degreeDiploma;
    }

    public void setDegreeDiploma(String degreeDiploma) {
        this.degreeDiploma = degreeDiploma;
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
