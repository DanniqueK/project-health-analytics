package com.project.health_analytics.controller;

import com.project.health_analytics.annotation.AllowedUserTypes;
import com.project.health_analytics.model.UserType;
import com.project.health_analytics.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/session")
public class SessionController {
    @Autowired
    private SessionService sessionService;

    // Create

    // Creating a session is done through the login endpoint.

    // Read

    // getting a session(and validating it) is all done through the
    // SessionInterceptor

    // Update

    // Delete

    /**
     * Deletes a session by its session ID.
     *
     * @param sessionId The ID of the session to delete.
     * @return A ResponseEntity indicating the result of the operation.
     */
    @DeleteMapping("/delete")
    @AllowedUserTypes({ UserType.patient, UserType.medical_professional, UserType.dentist, UserType.admin })
    public ResponseEntity<Void> deleteSessionBySessionId(@RequestParam String sessionId) {
        boolean isDeleted = sessionService.deleteSessionBySessionId(sessionId);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}