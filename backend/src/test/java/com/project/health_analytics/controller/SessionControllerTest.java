package com.project.health_analytics.controller;

import com.project.health_analytics.service.SessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class SessionControllerTest {

    @InjectMocks
    private SessionController sessionController;

    @Mock
    private SessionService sessionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDeleteSessionBySessionId_Success() {
        // Given
        String sessionId = "validSessionId";
        when(sessionService.deleteSessionBySessionId(sessionId)).thenReturn(true);

        // When
        ResponseEntity<Void> response = sessionController.deleteSessionBySessionId(sessionId);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(sessionService, times(1)).deleteSessionBySessionId(sessionId);
    }

    @Test
    void testDeleteSessionBySessionId_NotFound() {
        // Given
        String sessionId = "invalidSessionId";
        when(sessionService.deleteSessionBySessionId(sessionId)).thenReturn(false);

        // When
        ResponseEntity<Void> response = sessionController.deleteSessionBySessionId(sessionId);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(sessionService, times(1)).deleteSessionBySessionId(sessionId);
    }
}