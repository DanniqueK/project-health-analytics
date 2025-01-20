package com.project.health_analytics.controller;

import com.project.health_analytics.dto.UserTypeDto;
import com.project.health_analytics.model.UserType;
import com.project.health_analytics.service.SessionService;
import com.project.health_analytics.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jakarta.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private SessionService sessionService;

    @Mock
    private HttpServletRequest request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserRole_ValidSession() {
        // Given
        UserType userType = UserType.medical_professional;
        when(request.getAttribute("userType")).thenReturn(userType);

        // When
        ResponseEntity<UserTypeDto> response = userController.getUserRole(request);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userType, response.getBody().getUserType());
    }

    @Test
    void testGetUserRole_InvalidSession() {
        // Given
        when(request.getAttribute("userType")).thenReturn(null);

        // When
        ResponseEntity<UserTypeDto> response = userController.getUserRole(request);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}