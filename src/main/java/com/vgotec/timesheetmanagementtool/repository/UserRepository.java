package com.vgotec.timesheetmanagementtool.repository;

import com.vgotec.timesheetmanagementtool.entity.User;
//import com.vgotec.timesheetmanagementtool.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    Optional<User> findByDisplayName(String displayName); // ✅ if your entity uses displayName

    Optional<User> findByUserType(String userType); // ✅ match your entity field name

    Optional<User> findByUserTypeIgnoreCase(String userType); // (optional helper)

    // Optional<User> findByRole(Role role); // ✅ keep if Role is used
}
