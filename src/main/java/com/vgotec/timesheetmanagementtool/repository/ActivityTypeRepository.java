package com.vgotec.timesheetmanagementtool.repository;

import com.vgotec.timesheetmanagementtool.entity.ActivityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ActivityTypeRepository extends JpaRepository<ActivityType, UUID> {
    Optional<ActivityType> findByName(String Name);

    Optional<ActivityType> findByNameAndCategory(String Name, String category); // 🆕
}
