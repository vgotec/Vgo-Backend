package com.vgotec.timesheetmanagementtool.controller.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/forms")
public interface FormController {

    /**
     * Get a form JSON definition by name.
     *
     * @param formName Name of the form JSON file (without extension)
     * @return JSON string of the form definition
     */
    ResponseEntity<String> getForm(@PathVariable String formName);
}
