package com.project.health_analytics.controller;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.health_analytics.dto.ErrorResponse;
import com.project.health_analytics.dto.LoginDto;
import com.project.health_analytics.dto.SessionReturnDto;
import com.project.health_analytics.service.LoginService;
import com.project.health_analytics.util.Constants;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * Handles POST requests to authenticate a user based on the provided login
     * 
     * @param loginDto the login credentials.
     * 
     */
    @PostMapping("/user/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        try {
            SessionReturnDto sessionReturn = loginService.login(loginDto);

            if (sessionReturn != null) {
                HttpHeaders headers = new HttpHeaders();
                ResponseCookie sessionCookie = ResponseCookie.from("sessionId", sessionReturn.getSessionId())
                        .httpOnly(true)
                        .secure(false) // Set to false for development
                        .path("/")
                        .maxAge(Duration.ofMillis(Constants.SESSION_EXPIRY_TIME))
                        .sameSite("Lax")
                        .build();

                headers.add(HttpHeaders.SET_COOKIE, sessionCookie.toString());

                Map<String, String> responseBody = new HashMap<>();
                responseBody.put("userType", sessionReturn.getUserType().getUserType().toString());

                return ResponseEntity.ok()
                        .headers(headers)
                        .body(responseBody);
            } else {
                return handleError(new RuntimeException(Constants.INVALID_CREDENTIALS_MESSAGE));
            }
        } catch (Exception e) {
            return handleError(e);
        }
    }

    /**
     * Handles exceptions and returns appropriate error responses.
     *
     * @param e The exception to handle
     * @return ResponseEntity with appropriate status code and error message
     */
    private ResponseEntity<ErrorResponse> handleError(Exception e) {
        if (e instanceof RuntimeException &&
                Constants.INVALID_CREDENTIALS_MESSAGE.equals(e.getMessage())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(Constants.INVALID_CREDENTIALS_MESSAGE));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(Constants.INTERNAL_SERVER_ERROR_MESSAGE));
    }
}
