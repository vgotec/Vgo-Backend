package com.vgotec.timesheetmanagementtool.util;

//import com.vgotec.timesheetmanagementtool.enums.Role;

public class UserServiceUtil {

    /**
     * ✅ Converts a role string to the Role enum safely
     */
    // public static Role parseRole(Role role) {
    // try {
    // return Role.setRole(role);
    // } catch (IllegalArgumentException | NullPointerException e) {
    // throw new RuntimeException("Invalid role: " + role);
    // }
    // }

    /**
     * ✅ Validates if the input string is null or empty
     */
    public static void validateNotEmpty(String field, String fieldName) {
        if (field == null || field.trim().isEmpty()) {
            throw new RuntimeException(fieldName + " cannot be empty");
        }
    }
}
