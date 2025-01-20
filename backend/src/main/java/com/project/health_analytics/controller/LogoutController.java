package com.project.health_analytics.controller;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.health_analytics.annotation.AllowedUserTypes;
import com.project.health_analytics.model.UserType;
import com.project.health_analytics.service.SessionService;
import com.project.health_analytics.util.Constants;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller responsible for handling user logout operations.
 * Manages the deletion of session cookies and cleanup of session data from the
 * database.
 * 
 */
@RestController
public class LogoutController {

    @Autowired
    private SessionService sessionService;

    /**
     * Handles user logout requests by invalidating the session and removing
     * cookies.
     * 
     * @param request The HTTP request containing session cookies
     * @return ResponseEntity<?> containing:
     *         - Status 200 OK with success message if logout is successful
     *         - Status 500 Internal Server Error if:
     *         - Session ID is missing or invalid
     *         - Database operation fails
     *         - Any other unexpected error occurs
     * @throws Exception if there's an unexpected error during logout process
     */
    @DeleteMapping("/user/logout")
    @AllowedUserTypes({ UserType.patient, UserType.medical_professional, UserType.dentist, UserType.admin })
    public ResponseEntity<?> logout(HttpServletRequest request) {
        try {
            // Get session cookie
            Cookie[] cookies = request.getCookies();
            String sessionId = null;

            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("sessionId".equals(cookie.getName())) {
                        sessionId = cookie.getValue();
                        break;
                    }
                }
            }

            if (sessionId == null) {
                return createErrorResponse();
            }

            // Delete session from database
            boolean isDeleted = sessionService.deleteSessionBySessionId(sessionId);

            if (!isDeleted) {
                return createErrorResponse();
            }

            // Create response with cookie deletion
            HttpHeaders headers = new HttpHeaders();
            ResponseCookie deleteCookie = ResponseCookie.from("sessionId", "")
                    .httpOnly(true)
                    .secure(false) // Set to true in production
                    .path("/")
                    .maxAge(Duration.ZERO)
                    .sameSite("Lax")
                    .build();

            headers.add(HttpHeaders.SET_COOKIE, deleteCookie.toString());

            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("message", Constants.LOGOUT_SUCCESSFUL_MESSAGE);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(responseBody);

        } catch (Exception e) {
            return createErrorResponse();
        }
    }

    /**
     * Creates a standardized error response for logout failures.
     * 
     * @return ResponseEntity<?> with status 500 and error message
     */
    // TODO: discuss with team if the error response we expect is the same
    // everywhere, if not lets make it standardized so i only need the below
    // function
    private ResponseEntity<?> createErrorResponse() {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", Constants.INTERNAL_SERVER_ERROR_MESSAGE);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }
}