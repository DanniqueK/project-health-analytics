package com.project.health_analytics.repository;

import com.project.health_analytics.model.Password;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Password model
 */
@Repository
public interface PasswordRepository extends JpaRepository<Password, Integer> {
}