package com.vgotec.timesheetmanagementtool.service.impl;

import com.vgotec.timesheetmanagementtool.entity.TimesheetStatusColor;
import com.vgotec.timesheetmanagementtool.enums.TimeSheetStatus;
import com.vgotec.timesheetmanagementtool.repository.TimesheetStatusColorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimesheetStatusColorService {

    private final TimesheetStatusColorRepository repository;

    public TimesheetStatusColorService(TimesheetStatusColorRepository repository) {
        this.repository = repository;
    }

    public String getColorForStatus(TimeSheetStatus status) {
        return repository.findByStatus(status)
                .map(TimesheetStatusColor::getColorCode)
                .orElse("#E0E0E0"); // Default gray if not found
    }

    public List<TimesheetStatusColor> getAllStatusColors() {
        return repository.findAll();
    }
}
