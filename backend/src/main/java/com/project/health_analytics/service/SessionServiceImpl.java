package com.project.health_analytics.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.health_analytics.dto.SessionReturnDto;
import com.project.health_analytics.dto.UserTypeDto;
import com.project.health_analytics.mapper.SessionMapper;
import com.project.health_analytics.model.Session;
import com.project.health_analytics.model.User;
import com.project.health_analytics.repository.SessionRepository;
import com.project.health_analytics.repository.UserRepository;
import com.project.health_analytics.util.Constants;

/**
 * Service implementation for managing user sessions.
 * Handles creation, verification, and deletion of sessions.
 */
@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Creates a new session for the given BSN.
     *
     * @param bsn The BSN of the user
     * @return SessionReturnDto containing the new session information
     * @throws RuntimeException if session creation or retrieval fails
     */
    @Override
    public SessionReturnDto createSession(Integer bsn) {
        if (bsn == null) {
            throw new RuntimeException(Constants.INVALID_OR_MISSING_SESSION_ID);
        }

        deleteExistingSession(bsn);
        String sessionId = generateSessionId();
        Timestamp expiryDate = generateExpiryDate();

        createAndSaveSession(bsn, sessionId, expiryDate);

        return retrieveAndMapSession(sessionId);
    }

    /**
     * Retrieves user type information based on session ID.
     *
     * @param sessionId The session identifier
     * @return UserTypeDto containing the user's type
     * @throws RuntimeException if session or user is not found
     */
    @Override
    public UserTypeDto getUserTypeBySessionId(String sessionId) {
        Session session = findSessionById(sessionId);
        User user = findUserByBsn(session.getBsn());
        return new UserTypeDto(user.getUserType());
    }

    /**
     * Verifies session existence and returns associated BSN.
     *
     * @param sessionId The session identifier to verify
     * @return BSN associated with the session
     * @throws RuntimeException if session not found or expired
     */
    @Override
    public Integer verifySessionAndGetBsn(String sessionId) {
        return sessionRepository.findBySessionId(sessionId)
                .map(session -> {
                    if (session.getExpiryDate().before(Timestamp.from(Instant.now()))) {
                        throw new RuntimeException(Constants.SESSION_NOT_FOUND + sessionId);
                    }
                    return session.getBsn();
                })
                .orElse(null);
    }

    /**
     * Deletes a session by its identifier.
     *
     * @param sessionId The session identifier to delete
     * @return true if deletion was successful
     * @throws RuntimeException if session not found or deletion fails
     */
    @Override
    public boolean deleteSessionBySessionId(String sessionId) {
        if (sessionId == null || sessionId.trim().isEmpty()) {
            throw new RuntimeException(Constants.INVALID_OR_MISSING_SESSION_ID);
        }

        Session session = sessionRepository.findBySessionId(sessionId)
                .orElseThrow(() -> new RuntimeException(Constants.SESSION_NOT_FOUND + sessionId));

        try {
            sessionRepository.delete(session);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(Constants.INTERNAL_SERVER_ERROR_MESSAGE);
        }
    }

    /**
     * Retrieves and deletes any existing session for the given BSN.
     *
     * @param bsn The BSN to check for existing sessions
     */
    private void deleteExistingSession(Integer bsn) {
        sessionRepository.findByBsn(bsn)
                .ifPresent(sessionRepository::delete);
    }

    /**
     * Creates and saves a new session.
     *
     * @param bsn        The BSN for the session
     * @param sessionId  The generated session identifier
     * @param expiryDate The session expiry timestamp
     * @throws RuntimeException if session save fails
     */
    private void createAndSaveSession(Integer bsn, String sessionId, Timestamp expiryDate) {
        try {
            Session session = new Session(bsn, sessionId, expiryDate);
            sessionRepository.save(session);
        } catch (Exception e) {
            throw new RuntimeException(Constants.INTERNAL_SERVER_ERROR_MESSAGE);
        }
    }

    /**
     * Generates a unique session identifier.
     *
     * @return String containing the generated UUID
     */
    private String generateSessionId() {
        return UUID.randomUUID().toString();
    }

    /**
     * Generates session expiry timestamp.
     *
     * @return Timestamp for session expiry
     */
    private Timestamp generateExpiryDate() {
        return Timestamp.from(Instant.now().plusSeconds(Constants.SESSION_EXPIRY_TIME));
    }

    /**
     * Retrieves and maps a session to DTO.
     *
     * @param sessionId The session identifier to retrieve
     * @return SessionReturnDto containing session information
     * @throws RuntimeException if session retrieval fails
     */
    private SessionReturnDto retrieveAndMapSession(String sessionId) {
        Session session = findSessionById(sessionId);
        return SessionMapper.sessionToReturnDto(session);
    }

    /**
     * Finds a session by its identifier.
     *
     * @param sessionId The session identifier to find
     * @return Session if found
     * @throws RuntimeException if session not found
     */
    private Session findSessionById(String sessionId) {
        return sessionRepository.findBySessionId(sessionId)
                .orElseThrow(() -> new RuntimeException(Constants.SESSION_NOT_FOUND + sessionId));
    }

    /**
     * Finds a user by their BSN.
     *
     * @param bsn The BSN to search for
     * @return User if found
     * @throws RuntimeException if user not found
     */
    private User findUserByBsn(Integer bsn) {
        return userRepository.findById(bsn)
                .orElseThrow(() -> new RuntimeException(Constants.USER_NOT_FOUND + bsn));
    }
}