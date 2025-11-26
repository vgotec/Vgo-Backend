package com.vgotec.timesheetmanagementtool.controller.impl;

import com.vgotec.timesheetmanagementtool.controller.interfaces.IUserController;
import com.vgotec.timesheetmanagementtool.dto.SignupRequest;
import com.vgotec.timesheetmanagementtool.entity.User;
import com.vgotec.timesheetmanagementtool.repository.UserRepository;
import com.vgotec.timesheetmanagementtool.service.interfaces.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth") // ✅ Matches your interface
public class UserControllerImpl implements IUserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IUserService userService;

    // ------------------- CREATE USER -------------------
    @Override
    @PostMapping("/createuser")
    public ResponseEntity<?> createUser(@Valid @RequestBody SignupRequest req) {
        try {
            // ✅ Create the new user via service
            User user = userService.registerUser(req);

            // ✅ Hide sensitive info before sending response
            user.setPasswordHash(null);

            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ------------------- GET USER BY ID -------------------
    @Override
    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable UUID id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            user.get().setPasswordHash(null);
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(404).body("User not found with ID: " + id);
        }
    }

    // ------------------- DELETE USER -------------------
    @Override
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.status(404).body("User not found with id: " + id);
        }
        userRepository.deleteById(id);
        return ResponseEntity.ok("User deleted successfully.");
    }
}
