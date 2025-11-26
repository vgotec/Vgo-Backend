package com.vgotec.timesheetmanagementtool.controller.impl;

import com.vgotec.timesheetmanagementtool.controller.interfaces.FormController;
import com.vgotec.timesheetmanagementtool.service.interfaces.FormDefinitionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.UUID;

@RestController
public class FormControllerImpl implements FormController {

    private final FormDefinitionService formDefinitionService;

    public FormControllerImpl(FormDefinitionService formDefinitionService) {
        this.formDefinitionService = formDefinitionService;
    }

    @Override
    @GetMapping("/{formName}")
    public ResponseEntity<String> getForm(@PathVariable String formName) {
        String formJson = formDefinitionService.getFormJson(formName);
        return ResponseEntity.ok(formJson);
    }

    @GetMapping("/dashboard/charts")
    public ResponseEntity<String> getDashboardForm(
            @RequestParam UUID empId,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year) {

        // 🗓 Use current month/year if not passed
        LocalDate today = LocalDate.now();
        int finalMonth = (month == null) ? today.getMonthValue() : month;
        int finalYear = (year == null) ? today.getYear() : year;

        String dashboardJson = formDefinitionService.getDashboardJson(empId, finalMonth, finalYear);

        return ResponseEntity.ok(dashboardJson);
    }

}
