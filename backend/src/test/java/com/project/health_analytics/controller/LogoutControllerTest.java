package com.project.health_analytics.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.project.health_analytics.dto.UserTypeDto;
import com.project.health_analytics.model.UserType;
import com.project.health_analytics.service.SessionService;
import com.project.health_analytics.util.Constants;
import jakarta.servlet.http.Cookie;

@SpringBootTest
@AutoConfigureMockMvc
class LogoutControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SessionService sessionService;

    private static final String SESSION_COOKIE_NAME = "sessionId";
    private static final String TEST_SESSION_ID = "test-session-123";
    private static final Integer TEST_BSN = 123456789;
    private static final UserType TEST_USER_TYPE = UserType.medical_professional;

    @BeforeEach
    void setUp() {
        // Setup common session validation mocks
        UserTypeDto userTypeDto = new UserTypeDto();
        userTypeDto.setUserType(TEST_USER_TYPE);
        when(sessionService.getUserTypeBySessionId(TEST_SESSION_ID)).thenReturn(userTypeDto);
        when(sessionService.verifySessionAndGetBsn(TEST_SESSION_ID)).thenReturn(TEST_BSN);
    }

    @Test
    void testSuccessfulLogout() throws Exception {
        // Given
        when(sessionService.deleteSessionBySessionId(TEST_SESSION_ID)).thenReturn(true);

        // When & Then
        mockMvc.perform(delete("/user/logout")
                .cookie(new Cookie(SESSION_COOKIE_NAME, TEST_SESSION_ID)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(Constants.LOGOUT_SUCCESSFUL_MESSAGE))
                .andExpect(cookie().maxAge(SESSION_COOKIE_NAME, 0))
                .andExpect(cookie().path(SESSION_COOKIE_NAME, "/"));
    }

    @Test
    void testLogoutWithInvalidSession() throws Exception {
        // Given
        String invalidSessionId = "invalid-session-id";
        UserTypeDto userTypeDto = new UserTypeDto();
        userTypeDto.setUserType(UserType.patient);
        when(sessionService.getUserTypeBySessionId(invalidSessionId)).thenReturn(userTypeDto);
        when(sessionService.verifySessionAndGetBsn(invalidSessionId)).thenReturn(123);
        when(sessionService.deleteSessionBySessionId(invalidSessionId)).thenReturn(false);

        // When & Then
        mockMvc.perform(delete("/user/logout")
                .cookie(new Cookie(SESSION_COOKIE_NAME, invalidSessionId)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value(Constants.INTERNAL_SERVER_ERROR_MESSAGE));
    }

    @ParameterizedTest
    @ValueSource(strings = { "", " ", "invalid!@#$", "null", "undefined" })
    void testLogoutWithVariousSessionFormats(String sessionId) throws Exception {
        // Given
        UserTypeDto userTypeDto = new UserTypeDto();
        userTypeDto.setUserType(UserType.patient);
        when(sessionService.getUserTypeBySessionId(sessionId)).thenReturn(userTypeDto);
        when(sessionService.verifySessionAndGetBsn(sessionId)).thenReturn(123);
        when(sessionService.deleteSessionBySessionId(sessionId)).thenReturn(false);

        // When & Then
        mockMvc.perform(delete("/user/logout")
                .cookie(new Cookie(SESSION_COOKIE_NAME, sessionId)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value(Constants.INTERNAL_SERVER_ERROR_MESSAGE));
    }
}