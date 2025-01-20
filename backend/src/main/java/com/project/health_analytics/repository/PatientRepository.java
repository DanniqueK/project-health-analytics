package com.project.health_analytics.repository;

import com.project.health_analytics.dto.PatientDto;
import com.project.health_analytics.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for the Patient model.
 * 
 * @method searchPatientsByNameOrBsn - Search for patients by their bsn or name
 * @method findPatientsByPrimaryPhysician - Find patients by primary physician.
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

        /**
         * Search for patients by their bsn or name.
         *
         * @return A list of PatientDto objects representing users with the specified
         *         bsn or name
         */
        @Query("SELECT new com.project.health_analytics.dto.PatientDto(u.bsn, CONCAT(u.firstName, ' ', u.middleName, ' ', u.lastName), u.dateOfBirth) "
                        + "FROM User u JOIN Patient p ON u.bsn = p.bsn WHERE u.bsn = :bsn OR LOWER(CONCAT(u.firstName, ' ', u.middleName, ' ', u.lastName)) LIKE LOWER(CONCAT('%', :name, '%'))")
        List<PatientDto> searchPatientsByNameOrBsn(String name, Integer bsn);

        /**
         * Finds patients by their primary physician.
         *
         * @param big The identifier of the primary physician.
         * @return A list of PatientDto objects representing patients with the specified
         *         primary physician.
         */
        @Query("SELECT new com.project.health_analytics.dto.PatientDto(u.bsn, CONCAT(u.firstName, ' ', u.middleName, ' ', u.lastName), u.dateOfBirth) "
                        + "FROM User u JOIN Patient p ON u.bsn = p.bsn WHERE p.primaryPhysician = :big")
        List<PatientDto> findPatientsByPrimaryPhysician(String big);
}