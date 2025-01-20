package com.project.health_analytics.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.health_analytics.model.HealthData;
import com.project.health_analytics.repository.HealthDataRepository;

@SpringBootTest
class HealthDataServiceImplTest {

    @InjectMocks
    private HealthDataServiceImpl healthDataService;

    @Mock
    private HealthDataRepository healthDataRepository;

    private static final Integer TEST_BSN = 123456789;
    private static final Date TEST_DATE = Date.valueOf(LocalDate.now());

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private HealthData createCompleteTestData() {
        HealthData data = new HealthData();
        data.setPatientBsn(TEST_BSN);
        data.setDateOfCollection(TEST_DATE);
        data.setHeartRate((short) 75);
        data.setBloodPressure(120);
        data.setCholesterolLevel(180);
        data.setBloodSugarLevel(100);
        data.setBodyMassIndex(new BigDecimal("24.5"));
        data.setSleepPattern("Normal");
        data.setStepCount(8000);
        data.setCaloricIntake(2000);
        data.setExerciseDuration((short) 60);
        data.setMentalHealthAssessment("Stable");
        data.setDietaryHabit("Balanced");
        data.setSubstanceHistory("None");
        return data;
    }

    @Test
    void testGetHealthDataByPatientBsn_Success() {
        // Given
        HealthData testData = createCompleteTestData();
        List<HealthData> expectedData = List.of(testData);
        when(healthDataRepository.findByPatientBsn(TEST_BSN)).thenReturn(expectedData);

        // When
        List<HealthData> result = healthDataService.getHealthDataByPatientBsn(TEST_BSN);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        HealthData resultData = result.get(0);
        assertAll(
                () -> assertEquals(TEST_BSN, resultData.getPatientBsn()),
                () -> assertEquals(TEST_DATE, resultData.getDateOfCollection()),
                () -> assertEquals((short) 75, resultData.getHeartRate()),
                () -> assertEquals(120, resultData.getBloodPressure()));
        verify(healthDataRepository).findByPatientBsn(TEST_BSN);
    }

    @Test
    void testGetHealthDataByPatientBsn_DataFormat() {
        // Given
        HealthData testData = createCompleteTestData();
        List<HealthData> dataList = List.of(testData);
        when(healthDataRepository.findByPatientBsn(TEST_BSN)).thenReturn(dataList);

        // When
        List<HealthData> result = healthDataService.getHealthDataByPatientBsn(TEST_BSN);

        // Then
        assertNotNull(result);
        HealthData resultData = result.get(0);
        assertAll(
                () -> assertNotNull(resultData.getPatientBsn()),
                () -> assertNotNull(resultData.getDateOfCollection()),
                () -> assertTrue(resultData.getHeartRate() >= 0),
                () -> assertTrue(resultData.getBloodPressure() >= 0),
                () -> assertNotNull(resultData.getBodyMassIndex()),
                () -> assertNotNull(resultData.getMentalHealthAssessment()));
    }

    @Test
    void testGetHealthDataByPatientBsn_EmptyResult() {
        // Given
        when(healthDataRepository.findByPatientBsn(TEST_BSN)).thenReturn(new ArrayList<>());

        // When
        List<HealthData> result = healthDataService.getHealthDataByPatientBsn(TEST_BSN);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(healthDataRepository).findByPatientBsn(TEST_BSN);
    }
}