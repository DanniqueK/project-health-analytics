package com.project.health_analytics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.health_analytics.model.MedicalProfessional;
import java.util.Optional;

/**
 * Repository interface for MedicalProfessional model
 * 
 * @method findByContactEmail - Find MedicalProfessional by contact email
 */
@Repository
public interface MedicalProfessionalRepository extends JpaRepository<MedicalProfessional, Integer> {
    Optional<MedicalProfessional> findByContactEmail(String contactEmail);

}