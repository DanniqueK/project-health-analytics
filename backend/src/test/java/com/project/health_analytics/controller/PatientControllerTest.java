package com.project.health_analytics.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.health_analytics.dto.PatientDto;
import com.project.health_analytics.dto.SearchQueryDto;
import com.project.health_analytics.dto.UserTypeDto;
import com.project.health_analytics.model.UserType;
import com.project.health_analytics.service.MedicalProfessionalService;
import com.project.health_analytics.service.PatientService;
import com.project.health_analytics.service.SessionService;
import com.project.health_analytics.util.TestPatient;

import jakarta.servlet.http.Cookie;

@SpringBootTest
@AutoConfigureMockMvc
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PatientService patientService;

    @MockBean
    private MedicalProfessionalService medicalProfessionalService;

    @MockBean
    private SessionService sessionService;

    private static final String SESSION_COOKIE_NAME = "sessionId";
    private static final String TEST_SESSION_ID = "test-session-123";
    private static final Integer TEST_BSN = 123456789;
    private static final UserType TEST_USER_TYPE = UserType.medical_professional;

    @BeforeEach
    void setUp() {
        UserTypeDto userTypeDto = new UserTypeDto();
        userTypeDto.setUserType(TEST_USER_TYPE);
        when(sessionService.getUserTypeBySessionId(TEST_SESSION_ID)).thenReturn(userTypeDto);
        when(sessionService.verifySessionAndGetBsn(TEST_SESSION_ID)).thenReturn(TEST_BSN);
    }

    @Test
    void testSearchPatients_ByName() throws Exception {
        // Given
        String searchQuery = "John";
        SearchQueryDto searchQueryDto = new SearchQueryDto();
        searchQueryDto.setSearchQuery(searchQuery);

        List<PatientDto> patients = List.of(TestPatient.createPatientDto());
        when(patientService.searchPatientsByNameOrBsn(searchQuery)).thenReturn(patients);

        // When/Then
        mockMvc.perform(post("/patient-list")
                .cookie(new Cookie(SESSION_COOKIE_NAME, TEST_SESSION_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(searchQueryDto))) // Changed this line
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bsn").value(patients.get(0).getBsn()))
                .andExpect(jsonPath("$[0].name").value(patients.get(0).getName()));

        verify(patientService).searchPatientsByNameOrBsn(searchQuery);
    }

    @Test
    void testSearchPatients_ByBSN() throws Exception {
        // Given
        String searchQuery = "123456789";
        SearchQueryDto searchQueryDto = new SearchQueryDto();
        searchQueryDto.setSearchQuery(searchQuery);

        List<PatientDto> patients = List.of(TestPatient.createPatientDto());
        when(patientService.searchPatientsByNameOrBsn(searchQuery)).thenReturn(patients);

        // When/Then
        mockMvc.perform(post("/patient-list")
                .cookie(new Cookie(SESSION_COOKIE_NAME, TEST_SESSION_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(searchQueryDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bsn").value(patients.get(0).getBsn()));

        verify(patientService, times(1)).searchPatientsByNameOrBsn(searchQuery);
    }

    @Test
    void testSearchPatients_EmptyResult() throws Exception {
        // Given
        String searchQuery = "NonexistentPatient";
        SearchQueryDto searchQueryDto = new SearchQueryDto();
        searchQueryDto.setSearchQuery(searchQuery);

        when(patientService.searchPatientsByNameOrBsn(searchQuery)).thenReturn(List.of());

        // When/Then
        mockMvc.perform(post("/patient-list")
                .cookie(new Cookie(SESSION_COOKIE_NAME, TEST_SESSION_ID))
                .requestAttr("userType", TEST_USER_TYPE)
                .requestAttr("bsn", TEST_BSN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(searchQueryDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());

        verify(patientService, times(1)).searchPatientsByNameOrBsn(searchQuery);
    }

    @Test
    void testGetPatientsByPrimaryPhysician_Success() throws Exception {
        // Given
        String big = "12345678910";
        List<PatientDto> patients = TestPatient.createPatientDtoList();
        when(medicalProfessionalService.getBigByBsn(TEST_BSN)).thenReturn(big);
        when(patientService.getPatientsByPrimaryPhysician(big)).thenReturn(patients);

        // When/Then
        mockMvc.perform(get("/patient-list")
                .cookie(new Cookie(SESSION_COOKIE_NAME, TEST_SESSION_ID))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bsn").value(patients.get(0).getBsn()));

        verify(medicalProfessionalService).getBigByBsn(TEST_BSN);
        verify(patientService).getPatientsByPrimaryPhysician(big);
    }

    @Test
    void testGetPatientsByPrimaryPhysician_NotFound() throws Exception {
        // Given
        when(medicalProfessionalService.getBigByBsn(TEST_BSN)).thenReturn(null);

        // When/Then
        mockMvc.perform(get("/patient-list")
                .cookie(new Cookie(SESSION_COOKIE_NAME, TEST_SESSION_ID))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(medicalProfessionalService).getBigByBsn(TEST_BSN);
        verify(patientService, never()).getPatientsByPrimaryPhysician(any());
    }

    @Test
    void testGetPatientsByPrimaryPhysician_EmptyList() throws Exception {
        // Given
        String big = "12345678910";
        when(medicalProfessionalService.getBigByBsn(TEST_BSN)).thenReturn(big);
        when(patientService.getPatientsByPrimaryPhysician(big)).thenReturn(List.of());

        // When/Then
        mockMvc.perform(get("/patient-list")
                .cookie(new Cookie(SESSION_COOKIE_NAME, TEST_SESSION_ID))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());

        verify(medicalProfessionalService).getBigByBsn(TEST_BSN);
        verify(patientService).getPatientsByPrimaryPhysician(big);
    }
}