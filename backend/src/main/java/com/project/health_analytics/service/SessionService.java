package com.project.health_analytics.service;

import com.project.health_analytics.dto.SessionReturnDto;
import com.project.health_analytics.dto.UserTypeDto;

/**
 * Service interface for managing sessions.
 */
public interface SessionService {

    // Create

    /**
     * Creates a new session for the given BSN.
     *
     * @param bsn the BSN of the user
     * @return SessionIdDto, a DTO containing
     */
    SessionReturnDto createSession(Integer bsn);

    // Read

    /**
     * Retrieves the session ID for the given BSN.
     * 
     * @param bsn the BSN of the user
     */

    /**
     * Retrieves the UserType for the given sessionId
     * 
     * @param sessionId the sessionId of the user
     */
    UserTypeDto getUserTypeBySessionId(String sessionId);

    /**
     * Verifies the session and retrieves the BSN associated with the session ID.
     * This is done with every request.
     *
     * @param sessionId the session ID
     * @return the BSN if the session is valid, null otherwise
     */
    Integer verifySessionAndGetBsn(String sessionId);

    // Update

    // Delete

    /**
     * Deletes a session by its session ID.
     *
     * @param sessionId The ID of the session to delete.
     * @return true if the session was deleted, false otherwise.
     */
    boolean deleteSessionBySessionId(String sessionId);

}
