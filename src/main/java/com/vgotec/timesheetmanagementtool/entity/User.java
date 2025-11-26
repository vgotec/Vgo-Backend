package com.vgotec.timesheetmanagementtool.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "users_email_key", columnNames = "email"),
        @UniqueConstraint(name = "users_username_key", columnNames = "username")
})
public class User {

    /** Primary key — UUID (PostgreSQL gen_random_uuid()) */
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "uuid DEFAULT gen_random_uuid()", updatable = false, nullable = false)
    private UUID id;

    /** Username — unique login identifier */
    @Column(nullable = false, length = 150)
    private String username;

    /** Display name — full name shown in UI */
    @Column(name = "display_name", length = 200)
    private String displayName;

    /** Email — unique for each user */
    @Column(unique = true, length = 255)
    private String email;

    /** Hashed password */
    @Column(name = "password_hash", length = 255)
    private String passwordHash;

    /** Role / Type of user (ADMIN, MANAGER, EMPLOYEE, etc.) */
    @Column(name = "user_type", length = 50)
    private String userType;

    /** Record creation time */
    @Column(name = "created_at")
    private OffsetDateTime createdAt = OffsetDateTime.now();

    /** Record update time */
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt = OffsetDateTime.now();

    // ---------------- Constructors ----------------

    public User() {
    }

    public User(String username, String displayName, String email, String passwordHash, String userType) {
        this.username = username;
        this.displayName = displayName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.userType = userType;
    }

    // ---------------- Lifecycle Hooks ----------------

    @PrePersist
    public void onCreate() {
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = OffsetDateTime.now();
    }

    // ---------------- Getters & Setters ----------------

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
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
}
