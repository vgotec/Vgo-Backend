package com.vgotec.timesheetmanagementtool.service.impl;

import com.vgotec.timesheetmanagementtool.dto.SignupRequest;
import com.vgotec.timesheetmanagementtool.entity.User;
import com.vgotec.timesheetmanagementtool.repository.UserRepository;
import com.vgotec.timesheetmanagementtool.service.interfaces.IUserService;
import com.vgotec.timesheetmanagementtool.util.UserServiceUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public User registerUser(SignupRequest request) {
        // ✅ Validate required fields
        UserServiceUtil.validateNotEmpty(request.getEmail(), "Email");
        UserServiceUtil.validateNotEmpty(request.getName(), "Name");
        UserServiceUtil.validateNotEmpty(request.getPassword(), "Password");

        // ✅ Check for duplicate email
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use");
        }

        // ✅ Create new User entity
        User user = new User();
        user.setUsername(request.getEmail()); // Use email as username
        user.setDisplayName(request.getName());
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        // ✅ Map Role Enum → userType String (default to "EMPLOYEE" if null)
        user.setUserType(request.getRole() != null ? request.getRole().name() : "USER");

        // ✅ Set timestamps
        user.setCreatedAt(OffsetDateTime.now());
        user.setUpdatedAt(OffsetDateTime.now());

        // ✅ Save User to DB
        User savedUser = userRepository.save(user);

        return savedUser;
    }

    @Override
    public User loginUser(String email, String password) {
        // ✅ Validate inputs
        UserServiceUtil.validateNotEmpty(email, "Email");
        UserServiceUtil.validateNotEmpty(password, "Password");

        // ✅ Find User by Email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        // ✅ Compare passwords
        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new RuntimeException("Invalid email or password");
        }

        return user;
    }
}
