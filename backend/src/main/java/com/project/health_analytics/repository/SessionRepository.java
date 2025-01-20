package com.project.health_analytics.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.health_analytics.model.Session;

/**
 * Repository interface for Session model
 * 
 * @method findByBsn - Find Session by BSN
 * @method findBySessionId - Find Session by sessionId
 */
@Repository
public interface SessionRepository extends JpaRepository<Session, Integer> {
    /**
     * Find Session by BSN
     * 
     * @param bsn - BSN of the user
     * @return Session object
     */
    Optional<Session> findByBsn(Integer bsn);

    /**
     * Find Session by sessionId
     * 
     * @param sessionId - sessionId of the session
     * @return Session object
     */
    Optional<Session> findBySessionId(String sessionId);

}