package com.vgotec.timesheetmanagementtool.enums;

public enum LeaveStatus {
    NEW, // Just created, not yet submitted
    SUBMITTED, // Submitted by employee and awaiting review
    APPROVED, // Approved by manager or HR
    REJECTED, // Denied by manager/HR
    CANCELLED, // Cancelled by employee before approval
    COMPLETED // Leave period is over (optional state)
}
