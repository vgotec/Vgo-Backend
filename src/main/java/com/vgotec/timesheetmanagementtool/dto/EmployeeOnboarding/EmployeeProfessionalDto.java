package com.vgotec.timesheetmanagementtool.dto.EmployeeOnboarding;

public class EmployeeProfessionalDto {

    private Double experienceYears;
    private String location;
    private String skills;
    private String previousCompanyName;
    private String previousCompanyAddress;
    private String previousDesignation;

    // ----- Getters & Setters -----

    public Double getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(Double experienceYears) {
        this.experienceYears = experienceYears;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getPreviousCompanyName() {
        return previousCompanyName;
    }

    public void setPreviousCompanyName(String previousCompanyName) {
        this.previousCompanyName = previousCompanyName;
    }

    public String getPreviousCompanyAddress() {
        return previousCompanyAddress;
    }

    public void setPreviousCompanyAddress(String previousCompanyAddress) {
        this.previousCompanyAddress = previousCompanyAddress;
    }

    public String getPreviousDesignation() {
        return previousDesignation;
    }

    public void setPreviousDesignation(String previousDesignation) {
        this.previousDesignation = previousDesignation;
    }
}
