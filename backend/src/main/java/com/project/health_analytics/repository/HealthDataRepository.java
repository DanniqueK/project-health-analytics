package com.project.health_analytics.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.health_analytics.model.HealthData;

/**
 * Repository interface for HealthData model
 */
@Repository
public interface HealthDataRepository extends JpaRepository<HealthData, Integer> {

    @Query("SELECT h FROM HealthData h WHERE h.patientBsn = :bsn")
    List<HealthData> findByPatientBsn(@Param("bsn") Integer bsn);
}