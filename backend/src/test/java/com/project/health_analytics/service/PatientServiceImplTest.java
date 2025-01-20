package com.project.health_analytics.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.health_analytics.dto.PatientDto;
import com.project.health_analytics.repository.PatientRepository;
import com.project.health_analytics.util.TestPatient;

@SpringBootTest
class PatientServiceImplTest {

    @InjectMocks
    private PatientServiceImpl patientService;

    @Mock
    private PatientRepository patientRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should successfully get patients by primary physician")
    void testGetPatientsByPrimaryPhysician_Success() {
        // Given
        String big = "12345678910";
        List<PatientDto> expectedPatients = TestPatient.createPatientDtoList();
        when(patientRepository.findPatientsByPrimaryPhysician(big)).thenReturn(expectedPatients);

        // When
        List<PatientDto> result = patientService.getPatientsByPrimaryPhysician(big);

        // Then
        assertNotNull(result);
        assertEquals(expectedPatients.size(), result.size());
        assertEquals(expectedPatients.get(0).getName(), result.get(0).getName());
        verify(patientRepository, times(1)).findPatientsByPrimaryPhysician(big);
    }

    @Test
    @DisplayName("Should return empty list when no patients found for primary physician")
    void testGetPatientsByPrimaryPhysician_EmptyList() {
        // Given
        String big = "12345678910";
        when(patientRepository.findPatientsByPrimaryPhysician(big)).thenReturn(Collections.emptyList());

        // When
        List<PatientDto> result = patientService.getPatientsByPrimaryPhysician(big);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(patientRepository, times(1)).findPatientsByPrimaryPhysician(big);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException for invalid BIG number")
    void testGetPatientsByPrimaryPhysician_InvalidBig() {
        // Given
        String invalidBig = "123ABC";

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            patientService.getPatientsByPrimaryPhysician(invalidBig);
        });
    }

    @Test
    @DisplayName("Should successfully search patients by name")
    void testSearchPatientsByNameOrBsn_WithName() {
        // Given
        String searchQuery = "John";
        List<PatientDto> expectedPatients = List.of(TestPatient.createPatientDto());
        when(patientRepository.searchPatientsByNameOrBsn(searchQuery, null)).thenReturn(expectedPatients);

        // When
        List<PatientDto> result = patientService.searchPatientsByNameOrBsn(searchQuery);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.get(0).getName().contains(searchQuery));
        verify(patientRepository, times(1)).searchPatientsByNameOrBsn(searchQuery, null);
    }

    @Test
    @DisplayName("Should successfully search patients by BSN")
    void testSearchPatientsByNameOrBsn_WithBsn() {
        // Given
        String searchQuery = "123456789";
        Integer bsn = Integer.parseInt(searchQuery);
        List<PatientDto> expectedPatients = List.of(TestPatient.createPatientDto());
        when(patientRepository.searchPatientsByNameOrBsn(null, bsn)).thenReturn(expectedPatients);

        // When
        List<PatientDto> result = patientService.searchPatientsByNameOrBsn(searchQuery);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(bsn, result.get(0).getBsn());
        verify(patientRepository, times(1)).searchPatientsByNameOrBsn(null, bsn);
    }

    // Monkey tests / Edge cases
    @Test
    @DisplayName("Should handle null search query")
    void testSearchPatientsByNameOrBsn_NullQuery() {
        // When
        List<PatientDto> result = patientService.searchPatientsByNameOrBsn(null);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(patientRepository, never()).searchPatientsByNameOrBsn(any(), any());
    }

    @Test
    @DisplayName("Should handle empty search query")
    void testSearchPatientsByNameOrBsn_EmptyQuery() {
        // When
        List<PatientDto> result = patientService.searchPatientsByNameOrBsn("");

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(patientRepository, never()).searchPatientsByNameOrBsn(any(), any());
    }

    @Test
    @DisplayName("Should handle invalid BSN format")
    void testSearchPatientsByNameOrBsn_InvalidBsnFormat() {
        // Given
        String searchQuery = "12345ABC";

        // When
        List<PatientDto> result = patientService.searchPatientsByNameOrBsn(searchQuery);

        // Then
        assertNotNull(result);
        verify(patientRepository, times(1)).searchPatientsByNameOrBsn(searchQuery, null);
    }

    @Test
    @DisplayName("Should handle special characters in name search")
    void testSearchPatientsByNameOrBsn_SpecialCharacters() {
        // Given
        String searchQuery = "John@#$%";
        when(patientRepository.searchPatientsByNameOrBsn(searchQuery, null)).thenReturn(Collections.emptyList());

        // When
        List<PatientDto> result = patientService.searchPatientsByNameOrBsn(searchQuery);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(patientRepository, times(1)).searchPatientsByNameOrBsn(searchQuery, null);
    }

    @Test
    @DisplayName("Should handle extremely long search query")
    void testSearchPatientsByNameOrBsn_LongQuery() {
        // Given
        String searchQuery = "a".repeat(1000); // Very long query

        // When
        List<PatientDto> result = patientService.searchPatientsByNameOrBsn(searchQuery);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        // Don't verify repository call since it should be blocked by length validation
        verify(patientRepository, never()).searchPatientsByNameOrBsn(any(), any());
    }
}