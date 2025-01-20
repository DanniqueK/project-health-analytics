package com.project.health_analytics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.health_analytics.annotation.AllowedUserTypes;
import com.project.health_analytics.dto.UserTypeDto;
import com.project.health_analytics.model.User;
import com.project.health_analytics.model.UserType;
import com.project.health_analytics.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * REST controller for managing users.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // Create

    // Read

    /**
     * Handles GET requests to retrieve all users.
     *
     * @return ResponseEntity containing a list of all users and an HTTP status of
     *         OK.
     */
    @GetMapping("")
    @AllowedUserTypes({ UserType.medical_professional, UserType.dentist, UserType.admin })
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> allUsers = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(allUsers);
    }

    /**
     * Handles GET requests to retrieve a user by BSN.
     *
     * @param bsn The BSN of the user to retrieve.
     * @return ResponseEntity containing the user and an HTTP status of OK, or
     *         NOT_FOUND if the user is not found.
     */
    @GetMapping("/{bsn}")
    @AllowedUserTypes({ UserType.patient, UserType.medical_professional, UserType.dentist, UserType.admin })
    public ResponseEntity<User> getUserByBsn(@PathVariable Integer bsn) {
        User user = userService.getUserByBsn(bsn);
        if (user != null) {
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Handles GET requests to retrieve the user type based on the session ID.
     *
     * @param request The HTTP request containing the session ID.
     * @return ResponseEntity containing the UserTypeDto if found, or a NOT_FOUND
     *         status if not found.
     */
    @GetMapping("/role")
    @AllowedUserTypes({ UserType.patient, UserType.medical_professional, UserType.dentist, UserType.admin })
    public ResponseEntity<UserTypeDto> getUserRole(HttpServletRequest request) {
        UserType userType = (UserType) request.getAttribute("userType");
        if (userType != null) {
            UserTypeDto userTypeDto = new UserTypeDto(userType);
            return new ResponseEntity<>(userTypeDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Update

    // Delete
}