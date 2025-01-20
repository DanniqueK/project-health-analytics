package com.project.health_analytics.service;

public interface MedicalProfessionalService {
    /**
     * Retrieves the BIG number by BSN.
     *
     * @param bsn the BSN of the medical professional
     * @return the BIG number if found, null otherwise
     */
    String getBigByBsn(Integer bsn);
}
