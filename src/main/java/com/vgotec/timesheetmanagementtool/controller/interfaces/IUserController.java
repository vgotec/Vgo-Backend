package com.vgotec.timesheetmanagementtool.controller.interfaces;

import com.vgotec.timesheetmanagementtool.dto.SignupRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.UUID;

@RequestMapping("/api/auth") // ✅ Base path for authentication/user actions
public interface IUserController {

    // ------------------- CREATE USER -------------------
    @PostMapping("/createuser")
    ResponseEntity<?> createUser(@Valid @RequestBody SignupRequest req);

    // ------------------- GET USER BY ID -------------------
    @GetMapping("/users/{id}")
    ResponseEntity<?> getUserById(@PathVariable UUID id);

    // ------------------- DELETE USER -------------------
    @DeleteMapping("/users/{id}")
    ResponseEntity<?> deleteUser(@PathVariable UUID id);
}
