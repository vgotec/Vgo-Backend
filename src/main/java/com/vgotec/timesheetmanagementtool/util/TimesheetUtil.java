/*
 * 
 * 
 * 
 * import com.fasterxml.jackson.core.type.TypeReferenc
 * import com.fasterxml.jackson.databind.ObjectMapper;
 * import com.vgotec.timesheetmanagementtool.dto.ActivityRequest;
 * import com.vgotec.timesheetmanagementtool.dto.TimeSheetEntryRe
 * import com.vgotec.timesheetmanagementtool.entity.Activity
 * import com.vgotec.timesheetmanagementtool.entity.Project;
 * import com.vgotec.timesheetmanagementtool.entity.TimeSheet;
 * 
 * 
 * 
 * 
 * import java.time.Du
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * // ---------------------------------------
 * // BUILD ENTRY FROM DTO (Safe calculation)
 * // ---------------------------------------------------
 * 
 *    public static TimesheetEntry buildEnt
 * try entry = new Timeshe
 * entry.setTimesheet(ts);
 * entry.setProjectId(dto.getProjectId());
 * entry.setActivityTypeId(dto.getActivityTypeId());
 * entry.setActivityDescription(dto.getDes
 * entry.setStartTime(dto.getStartTime
 * 
 *  
 * 
 * 
 * entry.setCreatedAt(java.time.OffsetDateTime. w());
 * 
 * 
 *  
 * 
 * 
 * // ----------------------------------
 * // WORK HOURS CALCULATION — Null Safe
 * // ---------------------------------------------------
 * 
 * 
 * // Case 1: workHours is provided
 * dto.getWorkHours() != null
 *  
 * 
 * 
 * // Case 2: calculate from start and end time
 * dto.getStartTime() != null && dto.getEndTime() != n
 * ll) {
 * long minutes = Duratio
 *  
 * 
 * 
 * // Case 3: 
 *  
 * 
 * 
 * // ---------------------------------------
 * // TOTAL HOURS CALCULATION FOR A TIMESHEET
 * // ---------------------------------------------------
 * ic static double computeTotalHou
 * 
 * 
 * rn
 * 
 * ToDouble(e -> {
 * e.getDurationHours() != null
 * 
 * 
 * e.getStartTime() != null && e.getEndTime() != nul
 * ) {
 * long minutes = Duratio
 *  
 * }
 *   
 * })
 *  
 * 
 * 
 * // ----------------
 * // MAP ENTRY TO DTO
 * // ----------------------------------------
 * tatic TimeSheetEntryResponse toDto(
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * hours == 0 && e.getStartTime() != null && e.getEn
 * Time() != null) {
 * long minutes = Duration
 *  
 * 
 * 
 * ew TimeShe
 * e.getId(),
 * projectName,
 * activityType.getName(),
 * activityType.getCategory(),
 * e.getA
 * hours,
 * e.getStartTime(
 * e.getEndTime(),
 *   
 *  
 * 
 * 
 * // --------------
 * // LOAD FORM JSON
 * // ---------------------------------------------------
 * ic st
 * {
 * ClassPathResource resource = new ClassPathResource(path);
 *     return mapper.readV
 * 
 * 
 *  
 *  
 *  
 }
*/