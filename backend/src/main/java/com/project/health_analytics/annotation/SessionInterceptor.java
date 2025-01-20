package com.project.health_analytics.annotation;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

import com.project.health_analytics.service.SessionService;
import com.project.health_analytics.dto.UserTypeDto;

/**
 * Interceptor for handling session validation and user type retrieval.
 */
@Component
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private SessionService sessionService;

    /**
     * Pre-handle method to validate the session and retrieve the user type.
     *
     * @param request  The HTTP request.
     * @param response The HTTP response.
     * @param handler  The handler.
     * @return true if the session is valid and the user type is retrieved, false
     *         otherwise.
     * @throws ResponseStatusException if the session is invalid or missing.
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        // Handle preflight requests
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }

        System.out.println("Request URI: " + request.getRequestURI());
        System.out.println("Request Method: " + request.getMethod());

        // Check cached attributes
        if (request.getAttribute("bsn") != null && request.getAttribute("userType") != null) {
            System.out.println("Using cached attributes");
            return true;
        }

        // Get cookies and validate session
        Cookie[] cookies = request.getCookies();
        System.out.println("Cookies length: " + (cookies != null ? cookies.length : "null"));

        if (cookies == null) {
            System.out.println("Cookies are null");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        // Find session cookie
        Cookie sessionCookie = null;
        for (Cookie cookie : cookies) {
            if ("sessionId".equals(cookie.getName())) {
                System.out.println("Cookie name: " + cookie.getName() + ", value: " + cookie.getValue());
                sessionCookie = cookie;
                break;
            }
        }

        if (sessionCookie == null) {
            System.out.println("Session cookie not found");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        // Validate session and get user info
        String sessionId = sessionCookie.getValue();
        System.out.println("Session ID: " + sessionId);

        try {
            Integer bsn = sessionService.verifySessionAndGetBsn(sessionId);
            System.out.println("BSN: " + bsn);

            if (bsn != null) {
                UserTypeDto userTypeDto = sessionService.getUserTypeBySessionId(sessionId);
                System.out.println("User Type DTO: " + userTypeDto.getUserType());
                if (userTypeDto.getUserType() != null) {
                    request.setAttribute("bsn", bsn);
                    request.setAttribute("userType", userTypeDto.getUserType());
                    System.out.println("Session validated successfully");
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println("Session validation error: " + e.getMessage());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        // Handle any other unexpected validation failures
        System.out.println("Unknown session validation error");
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return false;
    }
}