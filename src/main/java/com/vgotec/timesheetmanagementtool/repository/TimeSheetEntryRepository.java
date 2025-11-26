package com.vgotec.timesheetmanagementtool.repository;

import com.vgotec.timesheetmanagementtool.entity.TimesheetEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface TimeSheetEntryRepository extends JpaRepository<TimesheetEntry, UUID> {

    List<TimesheetEntry> findByTimesheetId(UUID timesheetId);

    // -------------------------------------------------------------------------
    // 1️⃣ All entries for EMPLOYEE + MONTH → JOIN FETCH is allowed here
    // -------------------------------------------------------------------------
    @Query("""
            SELECT e FROM TimesheetEntry e
            JOIN FETCH e.timesheet t
            WHERE t.employeeId = :employeeId
              AND EXTRACT(YEAR FROM t.workDate) = :year
              AND EXTRACT(MONTH FROM t.workDate) = :month
            """)
    List<TimesheetEntry> findByEmployeeAndMonth(
            @Param("employeeId") UUID employeeId,
            @Param("year") int year,
            @Param("month") int month);

    // -------------------------------------------------------------------------
    // 2️⃣ Project Hours → ❌ FETCH NOT ALLOWED in scalar GROUP BY queries
    // -------------------------------------------------------------------------
    @Query("""
            SELECT e.projectId, SUM(e.durationHours)
            FROM TimesheetEntry e
            JOIN e.timesheet t
            WHERE t.employeeId = :employeeId
              AND EXTRACT(YEAR FROM t.workDate) = :year
              AND EXTRACT(MONTH FROM t.workDate) = :month
            GROUP BY e.projectId
            """)
    List<Object[]> getProjectHours(
            @Param("employeeId") UUID employeeId,
            @Param("year") int year,
            @Param("month") int month);

    // -------------------------------------------------------------------------
    // 3️⃣ Weekly Burn Rate → ❌ FETCH NOT ALLOWED
    // -------------------------------------------------------------------------
    @Query("""
            SELECT EXTRACT(WEEK FROM t.workDate), SUM(e.durationHours)
            FROM TimesheetEntry e
            JOIN e.timesheet t
            WHERE e.projectId = :projectId
            GROUP BY EXTRACT(WEEK FROM t.workDate)
            ORDER BY EXTRACT(WEEK FROM t.workDate)
            """)
    List<Object[]> getWeeklyBurnRate(
            @Param("projectId") UUID projectId);

    // -------------------------------------------------------------------------
    // 4️⃣ Project Load (Stacked Bar Chart) → ❌ FETCH NOT ALLOWED
    // -------------------------------------------------------------------------
    @Query("""
            SELECT e.projectId, e.activityDescription, SUM(e.durationHours)
            FROM TimesheetEntry e
            JOIN e.timesheet t
            WHERE EXTRACT(YEAR FROM t.workDate) = :year
              AND EXTRACT(MONTH FROM t.workDate) = :month
            GROUP BY e.projectId, e.activityDescription
            """)
    List<Object[]> getProjectLoad(
            @Param("year") int year,
            @Param("month") int month);
}
