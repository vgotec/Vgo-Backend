package com.vgotec.timesheetmanagementtool.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vgotec.timesheetmanagementtool.dto.dashboard.DashboardResponse;
import com.vgotec.timesheetmanagementtool.entity.ActivityType;
import com.vgotec.timesheetmanagementtool.repository.ActivityTypeRepository;
import com.vgotec.timesheetmanagementtool.repository.ProjectRepository;
import com.vgotec.timesheetmanagementtool.repository.TimeSheetRepository;
import com.vgotec.timesheetmanagementtool.repository.LeaveTypeRepository;
import com.vgotec.timesheetmanagementtool.service.interfaces.DashboardService;
import com.vgotec.timesheetmanagementtool.service.interfaces.FormDefinitionService;
import com.vgotec.timesheetmanagementtool.util.DashboardInjector;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FormDefinitionServiceImpl implements FormDefinitionService {

    private final ProjectRepository projectRepository;
    private final ActivityTypeRepository activityTypeRepository;
    private final TimeSheetRepository timeSheetRepository;
    private final TimesheetStatusColorService timeSheetStatusColorService;
    private final DashboardService dashboardService;
    private final LeaveTypeRepository leaveTypeRepository;
    private final ObjectMapper objectMapper;

    private final DashboardInjector dashboardInjector;

    public FormDefinitionServiceImpl(
            ProjectRepository projectRepository,
            ActivityTypeRepository activityTypeRepository,
            TimeSheetRepository timeSheetRepository,
            TimesheetStatusColorService timeSheetStatusColorService,
            DashboardService dashboardService,
            LeaveTypeRepository leaveTypeRepository,
            ObjectMapper objectMapper,
            DashboardInjector dashboardInjector) {
        this.projectRepository = projectRepository;
        this.activityTypeRepository = activityTypeRepository;
        this.timeSheetRepository = timeSheetRepository;
        this.timeSheetStatusColorService = timeSheetStatusColorService;
        this.dashboardService = dashboardService;
        this.leaveTypeRepository = leaveTypeRepository;
        this.objectMapper = objectMapper;
        this.dashboardInjector = dashboardInjector;
    }

    /**
     * Load a form by name and inject dropdown values
     */
    @Override
    public String getFormJson(String formName) {
        try {
            ClassPathResource resource = new ClassPathResource("forms/" + formName + ".json");
            String json = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

            JsonNode rootNode = objectMapper.readTree(json);

            // Inject only dropdown data
            injectDropdownData(rootNode, formName);

            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);

        } catch (IOException e) {
            throw new RuntimeException("❌ Unable to load form: " + formName, e);
        }
    }

    /**
     * Load charts_form.json and inject dynamic chart data
     */
    @Override
    public String getDashboardJson(UUID empId, int month, int year) {
        try {
            ClassPathResource resource = new ClassPathResource("forms/charts.json");
            String json = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

            JsonNode rootNode = objectMapper.readTree(json);

            DashboardResponse data = dashboardService.getDashboard(empId, month, year);

            // ⭐ Inject dynamic chart data into chart_form.json
            dashboardInjector.injectChartForm(rootNode, data);

            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);

        } catch (Exception e) {
            throw new RuntimeException("Unable to load dashboard layout JSON", e);
        }
    }

    /**
     * Inject dropdown values dynamically into a form
     */
    private void injectDropdownData(JsonNode rootNode, String formName) {

        List<ObjectNode> projectOptions = projectRepository.findAll().stream()
                .map(p -> {
                    ObjectNode opt = objectMapper.createObjectNode();
                    opt.put("label", p.getProjectCode() + " - " + p.getProjectName());
                    opt.put("value", p.getProjectCode() + " - " + p.getProjectName());
                    return opt;
                }).collect(Collectors.toList());

        List<ObjectNode> categoryOptions = activityTypeRepository.findAll().stream()
                .map(ActivityType::getCategory)
                .filter(c -> c != null && !c.isBlank())
                .distinct()
                .map(cat -> {
                    ObjectNode opt = objectMapper.createObjectNode();
                    opt.put("label", cat);
                    opt.put("value", cat);
                    return opt;
                }).collect(Collectors.toList());

        List<ObjectNode> activityOptions = activityTypeRepository.findAll().stream()
                .map(a -> {
                    ObjectNode opt = objectMapper.createObjectNode();
                    opt.put("label", a.getName());
                    opt.put("value", a.getName());
                    return opt;
                }).collect(Collectors.toList());

        replaceOptions(rootNode, "project", projectOptions);
        replaceOptions(rootNode, "activity", activityOptions);
        replaceOptions(rootNode, "category", categoryOptions);

        if (formName.equalsIgnoreCase("create_leave_form")) {
            List<ObjectNode> leaveTypeOptions = leaveTypeRepository.findAll().stream()
                    .map(l -> {
                        ObjectNode opt = objectMapper.createObjectNode();
                        opt.put("label", l.getName());
                        opt.put("value", l.getName());
                        return opt;
                    }).collect(Collectors.toList());

            replaceOptions(rootNode, "leaveType", leaveTypeOptions);
        }
    }

    /**
     * Replace options inside config.data.options for dropdown fields
     */
    private void replaceOptions(JsonNode rootNode, String key, List<ObjectNode> newOptions) {
        rootNode.findParents("key").forEach(node -> {
            if (node.get("key").asText().equals(key)) {
                ((ObjectNode) node.path("config").path("data"))
                        .set("options", objectMapper.valueToTree(newOptions));
            }
        });
    }
}
