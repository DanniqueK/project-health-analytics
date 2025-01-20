package com.project.health_analytics.service;

import java.util.List;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.health_analytics.dto.PatientDto;
import com.project.health_analytics.repository.PatientRepository;
import com.project.health_analytics.util.Constants;

/**
 * Implementation of the PatientService interface.
 * Provides business logic for patient-related operations.
 */
@Service
public class PatientServiceImpl implements PatientService {

    private static final String BIG_NUMBER_PATTERN = "^\\d{11}$";
    private static final int MAX_SEARCH_QUERY_LENGTH = 100;

    @Autowired
    private PatientRepository patientRepository;

    /**
     * Retrieves a list of patients assigned to a specific primary physician.
     *
     * @param big The BIG (professional registration) number of the physician
     * @return List of PatientDto objects associated with the given physician
     * @throws RuntimeException if the BIG number is invalid or processing fails
     */
    @Override
    public List<PatientDto> getPatientsByPrimaryPhysician(String big) {
        validateBigNumber(big);
        try {
            return patientRepository.findPatientsByPrimaryPhysician(big);
        } catch (Exception e) {
            throw new RuntimeException(Constants.DATABASE_ERROR + ": " + e.getMessage());
        }
    }

    /**
     * Validates BIG number format.
     *
     * @param big The BIG number to validate
     * @throws RuntimeException if BIG number format is invalid
     */
    private void validateBigNumber(String big) {
        if (big == null || !big.matches(BIG_NUMBER_PATTERN)) {
            throw new IllegalArgumentException(Constants.INVALID_BIG_NUMBER);
        }
    }

    /**
     * Searches for patients by their name or BSN (Burgerservicenummer).
     * If the search query is numeric, it searches by BSN.
     * If the search query is text, it searches by name.
     *
     * @param searchQuery The search string (can be either a name or BSN)
     * @return List of PatientDto objects matching the search criteria
     * @throws RuntimeException if database operation fails
     */
    @Override
    public List<PatientDto> searchPatientsByNameOrBsn(String searchQuery) {
        if (!isValidSearchQuery(searchQuery)) {
            return Collections.emptyList();
        }

        try {
            return performSearch(searchQuery.trim());
        } catch (Exception e) {
            throw new RuntimeException(Constants.DATABASE_ERROR + ": " + e.getMessage());
        }
    }

    /**
     * Performs the actual search operation based on the query type.
     *
     * @param query The validated and trimmed search query
     * @return List of PatientDto objects matching the search criteria
     */
    private List<PatientDto> performSearch(String query) {
        if (query.length() > MAX_SEARCH_QUERY_LENGTH) {
            return Collections.emptyList();
        }

        try {
            Integer bsn = Integer.parseInt(query);
            return patientRepository.searchPatientsByNameOrBsn(null, bsn);
        } catch (NumberFormatException e) {
            return patientRepository.searchPatientsByNameOrBsn(query, null);
        }
    }

    /**
     * Validates if a search query is valid (not null and not empty after trimming).
     *
     * @param searchQuery The search query to validate
     * @return true if the search query is valid, false otherwise
     */
    private boolean isValidSearchQuery(String searchQuery) {
        return searchQuery != null && !searchQuery.trim().isEmpty();
    }
}