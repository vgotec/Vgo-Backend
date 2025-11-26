package com.vgotec.timesheetmanagementtool.util;

import java.time.*;
import java.util.*;

public class DashboardUtil {

    private DashboardUtil() {
    } // prevent instantiation

    // ----------------------------------------------------------------
    // DATE CONVERSION → LocalDateTime / OffsetDateTime → Offset(+05:30)
    // ----------------------------------------------------------------
    public static OffsetDateTime toOffset(Object value) {
        if (value == null)
            return null;

        if (value instanceof OffsetDateTime odt)
            return odt;

        if (value instanceof LocalDateTime ldt)
            return ldt.atOffset(ZoneOffset.of("+05:30"));

        throw new IllegalArgumentException("Unknown datetime type: " + value.getClass());
    }

    // ----------------------------------------------------------------
    // COUNT WORKING DAYS (Excluding Weekends)
    // ----------------------------------------------------------------
    public static int countWorkingDays(int year, int month) {
        int days = YearMonth.of(year, month).lengthOfMonth();
        int count = 0;

        for (int d = 1; d <= days; d++) {
            DayOfWeek dow = LocalDate.of(year, month, d).getDayOfWeek();
            if (dow != DayOfWeek.SATURDAY && dow != DayOfWeek.SUNDAY)
                count++;
        }
        return count;
    }

    // ----------------------------------------------------------------
    // FILL MISSING MONTHS: 1–12
    // ----------------------------------------------------------------
    public static void fillMissingMonths(Map<Integer, Double> map) {
        for (int m = 1; m <= 12; m++) {
            map.putIfAbsent(m, 0.0);
        }
    }

    // ----------------------------------------------------------------
    // FILL MISSING DAYS (Used for Daily Hours Chart)
    // Type-safe for: Map<Integer, Map<String, Double>>
    // ----------------------------------------------------------------
    public static void fillMissingDays(Map<Integer, Map<String, Double>> map,
            int year,
            int month) {

        int days = YearMonth.of(year, month).lengthOfMonth();

        for (int d = 1; d <= days; d++) {
            map.putIfAbsent(d, new HashMap<>());
        }
    }

    // ----------------------------------------------------------------
    // CREATE EMPTY CATEGORY MAP
    // For Focus Area Breakdown Pie/Donut Chart
    // ----------------------------------------------------------------
    public static Map<String, Double> createCategoryMap() {
        return new HashMap<>();
    }
}
