package com.vgotec.timesheetmanagementtool.mapper;

public interface EntityMapper<D, E> {

    E toEntity(D dto);

    default E updateEntity(E entity, D dto) {
        return entity; // override if needed
    }
}
