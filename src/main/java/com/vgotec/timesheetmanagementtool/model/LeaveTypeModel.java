package com.vgotec.timesheetmanagementtool.model;

public class LeaveTypeModel {

    private Integer id;
    private String name;
    private Double yearlyLimit;

    // ---------- Constructors ----------
    public LeaveTypeModel() {
    }

    public LeaveTypeModel(Integer id, String name, Double yearlyLimit) {
        this.id = id;
        this.name = name;
        this.yearlyLimit = yearlyLimit;

    }

    // ---------- Getters and Setters ----------
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getYearlyLimit() {
        return yearlyLimit;
    }

    public void setYearlyLimit(Double yearlyLimit) {
        this.yearlyLimit = yearlyLimit;
    }

    // ---------- Optional toString ----------
    @Override
    public String toString() {
        return "LeaveTypeModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", yearlyLimit=" + yearlyLimit +

                '}';
    }
}
