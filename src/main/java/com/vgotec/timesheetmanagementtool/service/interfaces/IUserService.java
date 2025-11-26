package com.vgotec.timesheetmanagementtool.service.interfaces;

import com.vgotec.timesheetmanagementtool.dto.SignupRequest;
import com.vgotec.timesheetmanagementtool.entity.User;

public interface IUserService {

    /**
     * Registers a new user using the signup request DTO.
     *
     * @param request the signup request containing user details
     * @return the saved User entity
     */
    User registerUser(SignupRequest request);

    /**
     * Authenticates a user using email and password.
     *
     * @param email    the user’s email
     * @param password the user’s password
     * @return the authenticated User entity
     */
    User loginUser(String email, String password);
}
