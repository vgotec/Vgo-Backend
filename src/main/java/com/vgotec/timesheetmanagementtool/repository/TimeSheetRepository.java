package com.vgotec.timesheetmanagementtool.repository;

import com.vgotec.timesheetmanagementtool.entity.TimeSheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

@Repository
public interface TimeSheetRepository extends JpaRepository<TimeSheet, UUID> {

        Optional<TimeSheet> findByEmployeeIdAndWorkDate(UUID employeeId, LocalDate workDate);

        List<TimeSheet> findByEmployeeId(UUID employeeId);

        List<TimeSheet> findByEmployeeIdAndWorkDateBetween(
                        UUID employeeId,
                        LocalDate startDate,
                        LocalDate endDate);

        @Query("""
                            SELECT t FROM TimeSheet t
                            WHERE t.employeeId = :employeeId
                              AND EXTRACT(YEAR FROM t.workDate) = :year
                              AND EXTRACT(MONTH FROM t.workDate) = :month
                        """)
        List<TimeSheet> findByEmployeeIdAndMonth(
                        @Param("employeeId") UUID employeeId,
                        @Param("year") int year,
                        @Param("month") int month);

        /**
         * 🔹 This is the ONLY dashboard-related query
         * (used for Missing Days calculation)
         */
        @Query("""
                            SELECT DISTINCT t.workDate
                            FROM TimeSheet t
                            WHERE t.employeeId = :employeeId
                              AND EXTRACT(YEAR FROM t.workDate) = :year
                              AND EXTRACT(MONTH FROM t.workDate) = :month
                        """)
        List<LocalDate> findSubmittedDays(
                        @Param("employeeId") UUID employeeId,
                        @Param("year") int year,
                        @Param("month") int month);

        @Query("""
                            SELECT t.id, t.employeeId, t.status, t.workDate, t.updatedAt, t.remarks
                            FROM TimeSheet t
                            WHERE t.employeeId = :employeeId
                            ORDER BY t.updatedAt DESC
                        """)
        List<Object[]> getRecentTimesheetActivity(@Param("employeeId") UUID employeeId);

}
