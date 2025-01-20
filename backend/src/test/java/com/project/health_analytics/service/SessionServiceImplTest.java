package com.project.health_analytics.service;

import com.project.health_analytics.dto.SessionReturnDto;
import com.project.health_analytics.dto.UserTypeDto;
import com.project.health_analytics.model.Session;
import com.project.health_analytics.model.User;
import com.project.health_analytics.model.UserType;
import com.project.health_analytics.repository.SessionRepository;
import com.project.health_analytics.repository.UserRepository;
import com.project.health_analytics.util.Constants;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the SessionServiceImpl class.
 */
@SpringBootTest
class SessionServiceImplTest {

    @InjectMocks
    @Spy
    private SessionServiceImpl sessionService;

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @BeforeAll
    static void setup() {
        System.out.println("Setup for all tests");
    }

    @BeforeEach
    void setUp() {
        System.out.println("Setup for each test");
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
        System.out.println("Teardown for each test");
    }

    @AfterAll
    static void done() {
        System.out.println("Teardown for all tests");
    }

    /**
     * Black Box Test: Tests the creation of a session
     * Type: Unit Test
     * Input: BSN (Burger Service Nummer)
     * Expected: SessionReturnDto with session ID
     */
    @Test
    void testCreateSession() {
        System.out.println("Executing testCreateSession");
        // Given
        int bsn = 123456789;
        String sessionId = "sessionId";
        Session session = new Session(bsn, sessionId,
                Timestamp.from(Instant.now().plusMillis(Constants.SESSION_EXPIRY_TIME)));

        when(sessionRepository.save(any(Session.class))).thenReturn(session);
        when(sessionRepository.findBySessionId(any(String.class))).thenReturn(Optional.of(session));

        // When
        SessionReturnDto result = sessionService.createSession(bsn);

        // Then
        assertNotNull(result);
        assertEquals(sessionId, result.getSessionId());
        verify(sessionRepository, times(1)).save(any(Session.class));
        verify(sessionRepository, times(1)).findBySessionId(any(String.class));
    }

    @Test
    void testCreateSession_WithNullBsn() {
        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            sessionService.createSession(null);
        });
        assertEquals(Constants.INVALID_OR_MISSING_SESSION_ID, exception.getMessage());
    }

    @Test
    void testCreateSession_DatabaseError() {
        // Given
        int bsn = 123456789;
        when(sessionRepository.save(any(Session.class)))
                .thenThrow(new RuntimeException(Constants.INTERNAL_SERVER_ERROR_MESSAGE));

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            sessionService.createSession(bsn);
        });
        assertEquals(Constants.INTERNAL_SERVER_ERROR_MESSAGE, exception.getMessage());
    }

    /**
     * White Box Test: Tests getting user type by session ID
     * Type: Unit Test
     * Input: Session ID
     * Expected: UserTypeDto with user type
     */
    @Test
    void testGetUserTypeBySessionId() {
        System.out.println("Executing testGetUserTypeBySessionId");
        // Given
        String sessionId = "sessionId";
        int bsn = 123456789;
        Session session = new Session(bsn, sessionId,
                Timestamp.from(Instant.now().plusSeconds(Constants.SESSION_EXPIRY_TIME)));
        User user = new User();
        user.setBsn(bsn);
        user.setUserType(UserType.patient);

        when(sessionRepository.findBySessionId(sessionId)).thenReturn(Optional.of(session));
        when(userRepository.findById(bsn)).thenReturn(Optional.of(user));

        // When
        UserTypeDto result = sessionService.getUserTypeBySessionId(sessionId);

        // Then
        assertNotNull(result);
        assertEquals("patient", result.getUserType().toString());
    }

    /**
     * Black Box Test: Tests deleting a session by session ID
     * Type: Unit Test
     * Input: Session ID
     * Expected: True if session is deleted
     */
    @Test
    void testDeleteSessionBySessionId() {
        System.out.println("Executing testDeleteSessionBySessionId");
        // Given
        String sessionId = "sessionId";
        Session session = new Session(123456789, sessionId,
                Timestamp.from(Instant.now().plusSeconds(Constants.SESSION_EXPIRY_TIME)));

        when(sessionRepository.findBySessionId(sessionId)).thenReturn(Optional.of(session));

        // When
        boolean result = sessionService.deleteSessionBySessionId(sessionId);

        // Then
        assertTrue(result);
        verify(sessionRepository, times(1)).delete(session);
    }

    /**
     * White Box Test: Tests verifying session and getting BSN for a valid session
     * Type: Unit Test
     * Input: Valid session ID
     * Expected: BSN (Burger Service Nummer)
     */
    @Test
    void testVerifySessionAndGetBsn_ValidSession() {
        // Given
        String sessionId = "validSessionId";
        Session session = new Session(123456789, sessionId,
                Timestamp.from(Instant.now().plusSeconds(Constants.SESSION_EXPIRY_TIME)));
        when(sessionRepository.findBySessionId(sessionId)).thenReturn(Optional.of(session));

        // When
        Integer bsn = sessionService.verifySessionAndGetBsn(sessionId);

        // Then
        assertNotNull(bsn);
        assertEquals(123456789, bsn);
    }

    /**
     * White Box Test: Tests verifying session and getting BSN for an invalid
     * session
     * Type: Unit Test
     * Input: Invalid session ID
     * Expected: Null BSN
     */
    @Test
    void testVerifySessionAndGetBsn_InvalidSession() {
        // Given
        String sessionId = "invalidSessionId";
        when(sessionRepository.findBySessionId(sessionId)).thenReturn(Optional.empty());

        // When
        Integer bsn = sessionService.verifySessionAndGetBsn(sessionId);

        // Then
        assertNull(bsn);
    }

    /**
     * Property-based Test: Tests getting user type by various session ID formats
     * Type: Unit Test
     * Input: Different formats of session IDs
     * Expected: Consistent error handling
     */
    @Test
    void testGetUserTypeBySessionId_VariousFormats() {
        String[] sessionIds = { "", "null", "undefined", "invalid-format-!@#$", "12345" };

        for (String sessionId : sessionIds) {
            when(sessionRepository.findBySessionId(sessionId)).thenReturn(Optional.empty());

            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                sessionService.getUserTypeBySessionId(sessionId);
            });
            assertEquals(Constants.SESSION_NOT_FOUND + sessionId, exception.getMessage());
        }
    }

    @Test
    void testGetUserTypeBySessionId_RandomData() {
        // Given
        Random random = new Random();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        String sessionId = new String(bytes);

        when(sessionRepository.findBySessionId(sessionId)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            sessionService.getUserTypeBySessionId(sessionId);
        });
        assertEquals(Constants.SESSION_NOT_FOUND + sessionId, exception.getMessage());
    }

    /**
     * Monkey Test: Tests getting user type by session ID with random invalid data
     * Type: Unit Test
     * Input: Random invalid session ID
     * Expected: RuntimeException with session not found message
     */
    @Test
    void testGetUserTypeBySessionId_RandomInvalidData() {
        // Given
        Random random = new Random();
        int length = 20; // Length of the random session ID
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sessionIdBuilder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            sessionIdBuilder.append(characters.charAt(random.nextInt(characters.length())));
        }

        String sessionId = sessionIdBuilder.toString();
        when(sessionRepository.findBySessionId(sessionId)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            sessionService.getUserTypeBySessionId(sessionId);
        });
        assertEquals(Constants.SESSION_NOT_FOUND + sessionId, exception.getMessage());
    }

    /**
     * White Box Test: Tests getting user type by session ID for a valid session
     * Type: Unit Test
     * Input: Valid session ID
     * Expected: UserTypeDto with user type
     */
    @Test
    void testGetUserTypeBySessionId_ValidSession() {
        // Given
        String sessionId = "validSessionId";
        Session session = new Session(123456789, sessionId,
                Timestamp.from(Instant.now().plusSeconds(Constants.SESSION_EXPIRY_TIME)));
        User user = new User();
        user.setBsn(123456789);
        user.setUserType(UserType.medical_professional);
        when(sessionRepository.findBySessionId(sessionId)).thenReturn(Optional.of(session));
        when(userRepository.findById(123456789)).thenReturn(Optional.of(user));

        // When
        UserTypeDto userTypeDto = sessionService.getUserTypeBySessionId(sessionId);

        // Then
        assertNotNull(userTypeDto);
        assertEquals(UserType.medical_professional, userTypeDto.getUserType());
    }

    /**
     * White Box Test: Tests getting user type by session ID for an invalid session
     * Type: Unit Test
     * Input: Invalid session ID
     * Expected: RuntimeException with session not found message
     */
    @Test
    void testGetUserTypeBySessionId_InvalidSession() {
        // Given
        String sessionId = "invalidSessionId";
        when(sessionRepository.findBySessionId(sessionId)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            sessionService.getUserTypeBySessionId(sessionId);
        });
        assertEquals(Constants.SESSION_NOT_FOUND + sessionId, exception.getMessage());
    }

    /**
     * White Box Test: Tests getting user type by session ID when user is not found
     * Type: Unit Test
     * Input: Valid session ID but user not found
     * Expected: RuntimeException with user not found message
     */
    @Test
    void testGetUserTypeBySessionId_UserNotFound() {
        // Given
        String sessionId = "validSessionId";
        Session session = new Session(123456789, sessionId,
                Timestamp.from(Instant.now().plusSeconds(Constants.SESSION_EXPIRY_TIME)));
        when(sessionRepository.findBySessionId(sessionId)).thenReturn(Optional.of(session));
        when(userRepository.findById(123456789)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            sessionService.getUserTypeBySessionId(sessionId);
        });
        assertEquals(Constants.USER_NOT_FOUND + 123456789, exception.getMessage());
    }
}