package com.vgotec.timesheetmanagementtool.repository;

import com.vgotec.timesheetmanagementtool.entity.Leave;
import com.vgotec.timesheetmanagementtool.enums.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface LeaveRepository extends JpaRepository<Leave, UUID> {

        List<Leave> findByEmployeeIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                        UUID employeeId, LocalDate date1, LocalDate date2);

        List<Leave> findByEmployeeId(UUID employeeId);

        List<Leave> findByEmployeeIdAndStatus(UUID employeeId, LeaveStatus status);

        @Query("SELECT l FROM Leave l " +
                        "WHERE l.employeeId = :employeeId " +
                        "AND EXTRACT(YEAR FROM l.startDate) = :year " +
                        "AND EXTRACT(MONTH FROM l.startDate) = :month " +
                        "ORDER BY l.startDate ASC")
        List<Leave> findByEmployeeIdAndMonth(
                        @Param("employeeId") UUID employeeId,
                        @Param("year") int year,
                        @Param("month") int month);

        @Query("SELECT l FROM Leave l " +
                        "WHERE l.employeeId = :employeeId " +
                        "AND l.status = :status " +
                        "AND EXTRACT(YEAR FROM l.startDate) = :year " +
                        "AND EXTRACT(MONTH FROM l.startDate) = :month " +
                        "ORDER BY l.startDate ASC")
        List<Leave> findByEmployeeIdAndStatusForMonth(
                        @Param("employeeId") UUID employeeId,
                        @Param("status") LeaveStatus status,
                        @Param("year") int year,
                        @Param("month") int month);

        // -------------------------------------------------------------------------
        // 📌 Dashboard Query (NEW)
        // -------------------------------------------------------------------------

        /** 🔹 Leave Trend: SUM of leave days GROUP BY month */
        @Query("""
                            SELECT EXTRACT(MONTH FROM l.startDate) AS month,
                                   SUM(
                                       CASE
                                           WHEN l.session = 'FULL_DAY' THEN 1
                                           WHEN l.session = 'FIRST_HALF' THEN 0.5
                                           WHEN l.session = 'SECOND_HALF' THEN 0.5
                                           ELSE 0
                                       END
                                   ) AS totalDays
                            FROM Leave l
                            WHERE l.employeeId = :employeeId
                              AND EXTRACT(YEAR FROM l.startDate) = :year
                            GROUP BY EXTRACT(MONTH FROM l.startDate)
                            ORDER BY month
                        """)
        List<Object[]> getLeaveTrend(
                        @Param("employeeId") UUID employeeId,
                        @Param("year") int year);

        @Query("""
                            SELECT l.id, l.employeeId, lt.name, l.status, l.updatedAt, l.remarks
                            FROM Leave l
                            JOIN LeaveType lt ON lt.id = l.leaveTypeId
                            WHERE l.employeeId = :employeeId
                            ORDER BY l.updatedAt DESC
                        """)
        List<Object[]> getRecentLeaveActivity(@Param("employeeId") UUID employeeId);

}
