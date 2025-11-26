package com.vgotec.timesheetmanagementtool.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vgotec.timesheetmanagementtool.dto.dashboard.DashboardResponse;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Component
public class DashboardInjector {

    private final ObjectMapper objectMapper;

    public DashboardInjector(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * MAIN METHOD: inject data into both chart_form.json & charts_form.json
     */
    public void inject(JsonNode rootNode, DashboardResponse data) {

        Map<String, Object> map = buildDataMap(data);

        injectIntoChartForm(rootNode, map);
        injectIntoDashboardForm(rootNode, map);
    }

    /**
     * Build key → data map
     */
    private Map<String, Object> buildDataMap(DashboardResponse data) {

        Map<String, Object> map = new HashMap<>();

        map.put("timesheet_daily_hours", data.getDailyHours());
        map.put("dailyHours", data.getDailyHours());

        map.put("leave_usage_trend", data.getLeaveTrend());
        map.put("leaveTrend", data.getLeaveTrend());

        map.put("leave_kpi_section", data.getLeaveBalance());
        map.put("leaveBalance", data.getLeaveBalance());

        map.put("monthly_productivity_kpi", data.getProductivity());
        map.put("productivity", data.getProductivity());

        map.put("projectHours", data.getProjectHours());
        map.put("focusAreaBreakdown", data.getFocusAreaBreakdown());
        map.put("weeklyBurnRate", data.getWeeklyBurnRate());
        map.put("projectLoad", data.getProjectLoad());
        map.put("sessionBreakdown", data.getSessionBreakdown());
        map.put("missingDays", data.getMissingDays());
        map.put("recentActivity", data.getRecentActivity());

        return map;
    }

    /**
     * Inject data for chart_form.json (fields layout)
     */
    private void injectIntoChartForm(JsonNode rootNode, Map<String, Object> map) {

        JsonNode rows = rootNode.path("layout").path("rows");
        if (!rows.isArray())
            return;

        for (JsonNode row : rows) {
            JsonNode fields = row.path("fields");
            if (!fields.isArray())
                continue;

            for (JsonNode fieldNode : fields) {

                if (!(fieldNode instanceof ObjectNode))
                    continue;

                ObjectNode field = (ObjectNode) fieldNode;
                String key = field.path("key").asText();

                if (!map.containsKey(key))
                    continue;

                JsonNode configNode = field.path("config");
                if (!(configNode instanceof ObjectNode))
                    continue;

                ObjectNode config = (ObjectNode) configNode;
                config.set("data", objectMapper.valueToTree(map.get(key)));
            }
        }
    }

    /**
     * Inject data for charts_form.json (dashboardLayout)
     */
    private void injectIntoDashboardForm(JsonNode rootNode, Map<String, Object> map) {

        JsonNode widgets = rootNode.path("dashboardLayout");
        if (!widgets.isArray())
            return;

        for (JsonNode widgetNode : widgets) {

            if (!(widgetNode instanceof ObjectNode))
                continue;

            ObjectNode widget = (ObjectNode) widgetNode;
            String dataKey = widget.path("dataKey").asText();

            if (map.containsKey(dataKey)) {
                widget.set("data", objectMapper.valueToTree(map.get(dataKey)));
            }
        }
    }

    /**
     * Inject ONLY chart_form.json (layout.rows.fields structure)
     */
    public void injectChartForm(JsonNode rootNode, DashboardResponse data) {

        Map<String, Object> map = buildDataMap(data);

        JsonNode rows = rootNode.path("layout").path("rows");
        if (!rows.isArray())
            return;

        for (JsonNode row : rows) {
            JsonNode fields = row.path("fields");
            if (!fields.isArray())
                continue;

            for (JsonNode fieldNode : fields) {

                if (!(fieldNode instanceof ObjectNode))
                    continue;

                ObjectNode field = (ObjectNode) fieldNode;
                String key = field.path("key").asText();

                if (!map.containsKey(key))
                    continue;

                JsonNode configNode = field.path("config");
                if (!(configNode instanceof ObjectNode))
                    continue;

                ObjectNode config = (ObjectNode) configNode;

                // ⭐ Inject dynamic data
                config.set("data", objectMapper.valueToTree(map.get(key)));
            }
        }
    }

}
