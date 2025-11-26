package com.vgotec.timesheetmanagementtool.service.interfaces;

import java.util.UUID;

public interface FormDefinitionService {

    /**
     * Loads a JSON form definition from the resources/forms/ directory.
     *
     * @param formName The name of the form file (without .json extension)
     * @return The JSON content as a string
     */
    String getFormJson(String formName);

    String getDashboardJson(UUID empId, int month, int year);

}
