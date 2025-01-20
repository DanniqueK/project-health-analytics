package com.project.health_analytics.service;

import java.util.List;
import com.project.health_analytics.model.HealthData;

public interface HealthDataService {
    /**
     * Retrieves health data for a patient by their BSN.
     *
     * @param bsn The BSN of the patient
     * @return List of HealthData for the patient
     */
    List<HealthData> getHealthDataByPatientBsn(Integer bsn);
}