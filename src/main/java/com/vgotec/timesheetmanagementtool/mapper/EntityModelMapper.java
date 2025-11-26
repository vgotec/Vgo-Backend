package com.vgotec.timesheetmanagementtool.mapper;

import com.vgotec.timesheetmanagementtool.entity.TimeSheet;
import com.vgotec.timesheetmanagementtool.entity.User;
import com.vgotec.timesheetmanagementtool.model.TimesheetModel;
import com.vgotec.timesheetmanagementtool.model.UserModel;
import org.springframework.stereotype.Component;

@Component
public class EntityModelMapper {

    /*
     * ======================
     * TIMESHEET MAPPING
     * ======================
     */
    public TimesheetModel toTimesheetModel(TimeSheet entity) {
        if (entity == null) {
            return null;
        }

        TimesheetModel model = new TimesheetModel();
        model.setId(entity.getId());
        model.setEmployeeId(entity.getEmployeeId());
        model.setWorkDate(entity.getWorkDate());
        model.setTotalHours(entity.getTotalHours());
        model.setRemarks(entity.getRemarks());
        model.setStatus(entity.getStatus());
        model.setApprovedBy(entity.getApprovedBy());
        model.setApprovedAt(entity.getApprovedAt());
        model.setCreatedAt(entity.getCreatedAt());
        model.setUpdatedAt(entity.getUpdatedAt());
        return model;
    }

    public TimeSheet toTimesheetEntity(TimesheetModel model) {
        if (model == null) {
            return null;
        }

        TimeSheet entity = new TimeSheet();
        entity.setId(model.getId());
        entity.setEmployeeId(model.getEmployeeId());
        entity.setWorkDate(model.getWorkDate());
        entity.setTotalHours(model.getTotalHours());
        entity.setRemarks(model.getRemarks());
        entity.setStatus(model.getStatus());
        entity.setApprovedBy(model.getApprovedBy());
        entity.setApprovedAt(model.getApprovedAt());
        entity.setCreatedAt(model.getCreatedAt());
        entity.setUpdatedAt(model.getUpdatedAt());
        return entity;
    }

    /*
     * ======================
     * USER MAPPING
     * ======================
     */
    public UserModel toUserModel(User entity) {
        if (entity == null) {
            return null;
        }

        UserModel model = new UserModel();
        model.setId(entity.getId());
        model.setUsername(entity.getUsername());
        model.setDisplayName(entity.getDisplayName());
        model.setEmail(entity.getEmail());
        model.setUserType(entity.getUserType());
        return model;
    }

    public User toUserEntity(UserModel model) {
        if (model == null) {
            return null;
        }

        User entity = new User();
        entity.setId(model.getId());
        entity.setUsername(model.getUsername());
        entity.setDisplayName(model.getDisplayName());
        entity.setEmail(model.getEmail());
        entity.setUserType(model.getUserType());
        entity.setPasswordHash(null); // Never map passwords in DTOs
        return entity;
    }
}
