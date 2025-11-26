package com.vgotec.timesheetmanagementtool.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.vgotec.timesheetmanagementtool.entity.Leave;
import com.vgotec.timesheetmanagementtool.entity.LeaveType;

import org.springframework.core.io.ClassPathResource;

import java.util.*;

public class LeaveFormUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    private LeaveFormUtil() {
    } // prevent instantiation

    // -------------------------------------------------------
    // LOAD FORM JSON
    // -------------------------------------------------------
    public static Map<String, Object> loadForm(String path) {
        try {
            ClassPathResource resource = new ClassPathResource(path);
            return mapper.readValue(resource.getInputStream(), new TypeReference<>() {
            });
        } catch (Exception e) {
            throw new RuntimeException("Failed to load form JSON: " + path, e);
        }
    }

    // -------------------------------------------------------
    // INJECT LEAVE TYPE DROPDOWN
    // -------------------------------------------------------
    public static void injectLeaveTypeDropdown(
            Map<String, Object> formJson,
            List<LeaveType> leaveTypes) {

        List<Map<String, String>> leaveTypeOptions = leaveTypes.stream()
                .map(lt -> {
                    Map<String, String> opt = new LinkedHashMap<>();
                    opt.put("label", lt.getName());
                    opt.put("value", lt.getName());
                    return opt;
                })
                .toList();

        injectOptions(formJson, "leaveTypeName", leaveTypeOptions);
    }

    // -------------------------------------------------------
    // INJECT EXISTING LEAVE VALUES INTO MODIFY FORM
    public static void injectExistingLeaveValues(
            Map<String, Object> formJson,
            Leave leave,
            String leaveTypeName) {

        injectValue(formJson, "startDate", leave.getStartDate());
        injectValue(formJson, "endDate", leave.getEndDate());
        injectValue(formJson, "session", leave.getSession());
        injectValue(formJson, "remarks", leave.getRemarks());
        injectValue(formJson, "leaveTypeName", leaveTypeName); // correct key
        injectValue(formJson, "status", leave.getStatus().name());
    }

    // -------------------------------------------------------
    // INTERNAL HELPER — inject single value
    @SuppressWarnings("unchecked")
    private static void injectValue(Map<String, Object> formJson, String key, Object value) {

        Map<String, Object> layout = (Map<String, Object>) formJson.get("layout");
        if (layout == null)
            return;

        List<Map<String, Object>> rows = (List<Map<String, Object>>) layout.get("rows");
        if (rows == null)
            return;

        for (Map<String, Object> row : rows) {

            if (!row.containsKey("fields"))
                continue;

            List<Map<String, Object>> fields = (List<Map<String, Object>>) row.get("fields");

            for (Map<String, Object> field : fields) {

                if (key.equals(field.get("key"))) {

                    // ✔ Set defaultValue instead of value
                    field.put("defaultValue", value);

                    return;
                }
            }
        }
    }

    // -------------------------------------------------------
    // INTERNAL HELPER — inject dropdown options
    // -------------------------------------------------------
    @SuppressWarnings("unchecked")
    private static void injectOptions(Map<String, Object> formJson, String key, List<?> options) {

        Map<String, Object> layout = (Map<String, Object>) formJson.get("layout");
        if (layout == null)
            return;

        List<Map<String, Object>> rows = (List<Map<String, Object>>) layout.get("rows");
        if (rows == null)
            return;

        for (Map<String, Object> row : rows) {

            if (!row.containsKey("fields"))
                continue;

            List<Map<String, Object>> fields = (List<Map<String, Object>>) row.get("fields");

            for (Map<String, Object> field : fields) {

                if (key.equals(field.get("key"))) {

                    Map<String, Object> config = (Map<String, Object>) field.get("config");
                    if (config == null)
                        continue;

                    Map<String, Object> data = (Map<String, Object>) config.get("data");
                    if (data == null)
                        continue;

                    data.put("options", options);
                    return;
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static void injectLeaveId(Map<String, Object> formJson, UUID leaveId) {

        Map<String, Object> layout = (Map<String, Object>) formJson.get("layout");
        if (layout == null)
            return;

        List<Map<String, Object>> rows = (List<Map<String, Object>>) layout.get("rows");
        if (rows == null)
            return;

        for (Map<String, Object> row : rows) {

            if (!row.containsKey("fields"))
                continue;

            List<Map<String, Object>> fields = (List<Map<String, Object>>) row.get("fields");

            for (Map<String, Object> field : fields) {

                // Check for a field that has an action (button)
                if (field.containsKey("action")) {

                    Map<String, Object> action = (Map<String, Object>) field.get("action");
                    if (action == null)
                        continue;

                    Map<String, Object> payload = (Map<String, Object>) action.get("payload");
                    if (payload == null)
                        continue;

                    if (payload.containsKey("endpoint")) {
                        String endpoint = payload.get("endpoint").toString();

                        // Replace placeholder {leaveId}
                        endpoint = endpoint.replace("{leaveId}", leaveId.toString());

                        payload.put("endpoint", endpoint);
                    }
                }
            }
        }
    }

}
