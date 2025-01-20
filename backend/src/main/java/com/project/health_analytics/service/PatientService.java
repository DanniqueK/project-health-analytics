package com.project.health_analytics.service;

import java.util.List;

import com.project.health_analytics.dto.PatientDto;

/**
 * Service interface for managing patients.
 * Provides methods to retrieve and search patient information.
 */
public interface PatientService {

    /**
     * Retrieves a list of patients assigned to a specific primary physician.
     *
     * @param big The BIG (professional registration) number of the physician
     * @return List of PatientDto objects associated with the given physician
     * @throws IllegalArgumentException if the BIG number format is invalid
     */
    List<PatientDto> getPatientsByPrimaryPhysician(String big);

    /**
     * Searches for patients by their name or BSN (Burgerservicenummer).
     * If the search query is numeric, it searches by BSN.
     * If the search query is text, it searches by name.
     *
     * @param searchQuery The search string (can be either a name or BSN)
     * @return List of PatientDto objects matching the search criteria
     */
    List<PatientDto> searchPatientsByNameOrBsn(String searchQuery);
}