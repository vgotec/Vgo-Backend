package com.vgotec.timesheetmanagementtool.controller.impl;

import com.vgotec.timesheetmanagementtool.controller.interfaces.IAuthController;
import com.vgotec.timesheetmanagementtool.dto.LoginRequest;
import com.vgotec.timesheetmanagementtool.dto.LoginResponse;
import com.vgotec.timesheetmanagementtool.entity.User;
import com.vgotec.timesheetmanagementtool.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthControllerImpl implements IAuthController {

    private final IUserService userService;

    @Autowired
    public AuthControllerImpl(IUserService userService) {
        this.userService = userService;
    }

    // ------------------- LOGIN FOR ALL ROLES -------------------
    @Override
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            User user = userService.loginUser(
                    loginRequest.getEmail(),
                    loginRequest.getPassword());

            // ✅ Updated: removed metadata reference safely
            LoginResponse response = new LoginResponse(
                    user.getId(),
                    user.getDisplayName() != null ? user.getDisplayName() : user.getUsername(),
                    user.getUserType(),
                    null // avatarUrl (removed metadata, can handle later via separate profile field)
            );

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
