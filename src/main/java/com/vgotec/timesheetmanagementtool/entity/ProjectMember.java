package com.vgotec.timesheetmanagementtool.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "project_member", uniqueConstraints = {
        @UniqueConstraint(name = "project_member_project_id_employee_id_key", columnNames = { "project_id",
                "employee_id" })
}, indexes = {
        @Index(name = "idx_pm_project", columnList = "project_id"),
        @Index(name = "idx_pm_employee", columnList = "employee_id")
})
public class ProjectMember {

    /** Primary key — UUID */
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "uuid DEFAULT gen_random_uuid()", updatable = false, nullable = false)
    private UUID id;

    /** FK → project(id) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false, foreignKey = @ForeignKey(name = "project_member_project_id_fkey"))
    private Project project;

    /** FK → employee(id) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false, foreignKey = @ForeignKey(name = "project_member_employee_id_fkey"))
    private Employee employee;

    /** Role in project — e.g., "Developer", "Tester", "Manager" */
    @Column(length = 100)
    private String role;

    /** Date when assigned to project */
    @Column(name = "assigned_from")
    private LocalDate assignedFrom;

    /** Date when unassigned or contract ends */
    @Column(name = "assigned_to")
    private LocalDate assignedTo;

    /** Record creation timestamp */
    @Column(name = "created_at")
    private OffsetDateTime createdAt = OffsetDateTime.now();

    // ---------------- Lifecycle Hooks ----------------
    @PrePersist
    public void onCreate() {
        this.createdAt = OffsetDateTime.now();
    }

    // ---------------- Getters & Setters ----------------
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDate getAssignedFrom() {
        return assignedFrom;
    }

    public void setAssignedFrom(LocalDate assignedFrom) {
        this.assignedFrom = assignedFrom;
    }

    public LocalDate getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(LocalDate assignedTo) {
        this.assignedTo = assignedTo;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
