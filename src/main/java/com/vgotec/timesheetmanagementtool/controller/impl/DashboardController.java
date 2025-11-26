package com.vgotec.timesheetmanagementtool.controller.impl;

import com.vgotec.timesheetmanagementtool.dto.dashboard.DashboardResponse;
import com.vgotec.timesheetmanagementtool.service.interfaces.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/daily-hours")
    public ResponseEntity<?> getDailyHours(
            @RequestParam UUID userId,
            @RequestParam int month,
            @RequestParam int year) {
        return ResponseEntity.ok(dashboardService.getDailyHours(userId, month, year));
    }

    @GetMapping("/project-hours")
    public ResponseEntity<?> getProjectHours(
            @RequestParam UUID userId,
            @RequestParam int month,
            @RequestParam int year) {
        return ResponseEntity.ok(dashboardService.getProjectHours(userId, month, year));
    }

    @GetMapping("/leave-trend")
    public ResponseEntity<?> getLeaveTrend(
            @RequestParam UUID userId,
            @RequestParam int year) {
        return ResponseEntity.ok(dashboardService.getLeaveTrend(userId, year));
    }

    @GetMapping("/productivity")
    public ResponseEntity<?> getProductivity(
            @RequestParam UUID userId,
            @RequestParam int month,
            @RequestParam int year) {
        return ResponseEntity.ok(dashboardService.getProductivity(userId, month, year));
    }

    @GetMapping("/focus-area")
    public ResponseEntity<?> getFocusArea(
            @RequestParam UUID userId,
            @RequestParam int month,
            @RequestParam int year) {
        return ResponseEntity.ok(dashboardService.getFocusAreaBreakdown(userId, month, year));
    }

    @GetMapping("/burn-rate")
    public ResponseEntity<?> getBurnRate(@RequestParam UUID projectId) {
        return ResponseEntity.ok(dashboardService.getWeeklyBurnRate(projectId));
    }

    @GetMapping("/project-load")
    public ResponseEntity<?> getProjectLoad(
            @RequestParam int month,
            @RequestParam int year) {
        return ResponseEntity.ok(dashboardService.getProjectLoad(month, year));
    }

    @GetMapping("/sessions")
    public ResponseEntity<?> getSessionBreakdown(@RequestParam UUID userId) {
        return ResponseEntity.ok(dashboardService.getSessionBreakdown(userId));
    }

    @GetMapping("/missing-days")
    public ResponseEntity<?> getMissingDays(
            @RequestParam UUID userId,
            @RequestParam int month,
            @RequestParam int year) {
        return ResponseEntity.ok(dashboardService.getMissingDays(userId, month, year));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<DashboardResponse> getDashboard(
            @PathVariable UUID userId,
            @RequestParam int month,
            @RequestParam int year) {

        DashboardResponse response = dashboardService.getDashboard(userId, month, year);

        return ResponseEntity.ok(response);
    }

}
