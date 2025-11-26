package com.vgotec.timesheetmanagementtool.dto.EmployeeOnboarding;

import java.time.LocalDate;

public class EmployeeEducationDto {

    private String highestQualification;
    private String instituteName;
    private String degreeDiploma;
    private LocalDate completionDate;

    // ----- Getters & Setters -----
    public String getHighestQualification() {
        return highestQualification;
    }

    public void setHighestQualification(String highestQualification) {
        this.highestQualification = highestQualification;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public String getDegreeDiploma() {
        return degreeDiploma;
    }

    public void setDegreeDiploma(String degreeDiploma) {
        this.degreeDiploma = degreeDiploma;
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }
}
