package com.project.health_analytics.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.health_analytics.model.User;

/**
 * Repository interface for the User model.
 * 
 * @method findUserTypeByBsn - Find user type by BSN.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Finds the user type by BSN.
     *
     * @param bsn The BSN of the user.
     * @return An Optional containing the user type as a String.
     */
    @Query("SELECT u.userType FROM User u WHERE u.bsn = :bsn")
    Optional<String> findUserTypeByBsn(@Param("bsn") Integer bsn);
}