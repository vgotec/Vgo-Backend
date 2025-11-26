package com.vgotec.timesheetmanagementtool.controller.interfaces;

import com.vgotec.timesheetmanagementtool.dto.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface IAuthController {

    /**
     * Handles user login for all roles.
     *
     * @param loginRequest contains email and password
     * @return ResponseEntity with user details or error message
     */
    ResponseEntity<?> login(@RequestBody LoginRequest loginRequest);
}
