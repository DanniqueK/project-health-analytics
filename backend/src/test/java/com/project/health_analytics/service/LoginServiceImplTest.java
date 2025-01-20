package com.project.health_analytics.service;

import com.project.health_analytics.dto.LoginDto;
import com.project.health_analytics.dto.SessionReturnDto;
import com.project.health_analytics.model.MedicalProfessional;
import com.project.health_analytics.model.Password;
import com.project.health_analytics.model.User;
import com.project.health_analytics.repository.MedicalProfessionalRepository;
import com.project.health_analytics.repository.PasswordRepository;
import com.project.health_analytics.repository.UserRepository;
import com.project.health_analytics.util.Constants;
import com.project.health_analytics.util.TestLogin;
import com.project.health_analytics.util.TestUser;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the LoginServiceImpl class.
 */
@SpringBootTest
class LoginServiceImplTest {

    @InjectMocks
    private LoginServiceImpl loginService;

    @Mock
    private PasswordRepository passwordRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MedicalProfessionalRepository medicalProfessionalRepository;

    @Mock
    private SessionService sessionService;

    private User user;

    @BeforeAll
    static void setup() {
        System.out.println("Setup for all tests");
    }

    @BeforeEach
    void init() {
        System.out.println("Setup for each test");
        MockitoAnnotations.openMocks(this);
        user = TestUser.createUser();
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
     * Black Box Test: Tests the successful login scenario
     * Type: Unit Test
     * Input: Valid login data
     * Expected: SessionReturnDto with session ID
     */
    @Test
    void testSuccessfulLogin() {
        System.out.println("Should successfully login with valid login data");
        // Given
        LoginDto loginDto = TestLogin.createValidLoginDto();
        Password password = TestLogin.createValidPassword();
        user.setBsn(password.getBsn());

        when(passwordRepository.findById(Integer.parseInt(loginDto.getBsnOrEmail()))).thenReturn(Optional.of(password));
        when(userRepository.findById(Integer.parseInt(loginDto.getBsnOrEmail()))).thenReturn(Optional.of(user));
        when(sessionService.createSession(Integer.parseInt(loginDto.getBsnOrEmail())))
                .thenReturn(new SessionReturnDto("sessionId", null));

        // When
        SessionReturnDto result = loginService.login(loginDto);

        // Then
        assertNotNull(result);
        assertEquals("sessionId", result.getSessionId());
        verify(userRepository, times(1)).save(user);
    }

    /**
     * White Box Test: Tests the behavior when login fails due to invalid password
     * Type: Unit Test
     * Input: Invalid password
     * Expected: Null SessionReturnDto
     */
    @Test
    void testInvalidPasswordLogin() {
        // Given
        LoginDto loginDto = TestLogin.createInvalidPasswordLoginDto();
        Password password = TestLogin.createValidPassword();

        when(passwordRepository.findById(Integer.parseInt(loginDto.getBsnOrEmail())))
                .thenReturn(Optional.of(password));

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            loginService.login(loginDto);
        });
        assertEquals(Constants.INVALID_CREDENTIALS_MESSAGE, exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    /**
     * White Box Test: Tests the behavior when login fails due to nonexistent user
     * Type: Unit Test
     * Input: Nonexistent user data
     * Expected: Null SessionReturnDto
     */
    @Test
    void testNonexistentUserLogin() {
        // Given
        LoginDto loginDto = TestLogin.createNonexistentUserLoginDto();

        when(passwordRepository.findById(Integer.parseInt(loginDto.getBsnOrEmail())))
                .thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            loginService.login(loginDto);
        });
        assertEquals(Constants.INVALID_CREDENTIALS_MESSAGE, exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    /**
     * White Box Test: Tests the behavior when login fails due to expired password
     * Type: Unit Test
     * Input: Expired password data
     * Expected: Null SessionReturnDto
     */
    @Test
    void testExpiredPasswordLogin() {
        // Given
        LoginDto loginDto = TestLogin.createValidLoginDto();
        Password password = TestLogin.createExpiredPassword();

        when(passwordRepository.findById(Integer.parseInt(loginDto.getBsnOrEmail())))
                .thenReturn(Optional.of(password));

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            loginService.login(loginDto);
        });
        assertEquals(Constants.INVALID_CREDENTIALS_MESSAGE, exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    /**
     * Black Box Test: Tests the successful login scenario with valid email
     * Type: Unit Test
     * Input: Valid email data
     * Expected: SessionReturnDto with session ID
     */
    @Test
    void testLoginWithValidEmail() {
        System.out.println("Should successfully login with valid email");
        // Given
        LoginDto loginDto = TestLogin.createValidEmailDto();
        MedicalProfessional medicalProfessional = new MedicalProfessional();
        medicalProfessional.setContactEmail("john@gmail.com");
        medicalProfessional.setBsn(123456789);
        medicalProfessional.setContactEmail(loginDto.getBsnOrEmail());

        Password password = TestLogin.createValidPassword();
        user.setBsn(password.getBsn());

        when(medicalProfessionalRepository.findByContactEmail(loginDto.getBsnOrEmail()))
                .thenReturn(Optional.of(medicalProfessional));
        when(passwordRepository.findById(medicalProfessional.getBsn())).thenReturn(Optional.of(password));
        when(userRepository.findById(medicalProfessional.getBsn())).thenReturn(Optional.of(user));
        when(sessionService.createSession(medicalProfessional.getBsn()))
                .thenReturn(new SessionReturnDto("sessionId", null));

        // When
        SessionReturnDto result = loginService.login(loginDto);

        // Then
        assertNotNull(result);
        assertEquals("sessionId", result.getSessionId());
        verify(userRepository, times(1)).save(user);
    }

    /**
     * White Box Test: Tests the behavior when login fails due to invalid email
     * Type: Unit Test
     * Input: Invalid email data
     * Expected: Null SessionReturnDto
     */
    @Test
    void testLoginWithInvalidEmail() {
        // Given
        LoginDto loginDto = TestLogin.createInvalidEmailDto();

        when(medicalProfessionalRepository.findByContactEmail(loginDto.getBsnOrEmail()))
                .thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            loginService.login(loginDto);
        });
        assertEquals(Constants.INVALID_CREDENTIALS_MESSAGE, exception.getMessage());
        verify(passwordRepository, never()).findById(any());
        verify(userRepository, never()).findById(any());
        verify(sessionService, never()).createSession(any());
    }

    /**
     * White Box Test: Tests the behavior when login fails due to valid BSN but
     * invalid password
     * Type: Unit Test
     * Input: Valid BSN but invalid password
     * Expected: Null SessionReturnDto
     */
    @Test
    void testLoginWithValidBsnInvalidPassword() {
        // Given
        LoginDto loginDto = new LoginDto();
        loginDto.setBsnOrEmail("123456789");
        loginDto.setPassword("wrongPassword123"); // Different from valid password

        Password password = TestLogin.createValidPassword(); // Has hash of "validPassword123"

        when(passwordRepository.findById(Integer.parseInt(loginDto.getBsnOrEmail())))
                .thenReturn(Optional.of(password));

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            loginService.login(loginDto);
        });
        assertEquals(Constants.INVALID_CREDENTIALS_MESSAGE, exception.getMessage());
        verify(userRepository, never()).findById(any());
        verify(sessionService, never()).createSession(any());
    }

    /**
     * Property-based Test: Tests login with various formats of login data
     * Type: Unit Test
     * Input: Different formats of login data
     * Expected: Consistent error handling
     */
    @Test
    void testLoginWithVariousDataFormats() {
        String[] loginDataFormats = { "", "null", "undefined", "invalid-format-!@#$", "12345" };

        for (String loginData : loginDataFormats) {
            LoginDto loginDto = new LoginDto();
            loginDto.setBsnOrEmail(loginData);
            loginDto.setPassword("password");

            // When & Then
            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                loginService.login(loginDto);
            });

            // Verify exception message contains invalid credentials message
            assertEquals(Constants.INVALID_CREDENTIALS_MESSAGE, exception.getMessage());
        }
    }

    /**
     * Monkey Test: Tests login with random invalid data
     * Type: Unit Test
     * Input: Random invalid login data
     * Expected: RuntimeException with invalid credentials message
     */
    @Test
    void testLoginWithRandomInvalidData() {
        // Given
        Random random = new Random();
        int length = 20; // Length of the random login data
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder loginDataBuilder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            loginDataBuilder.append(characters.charAt(random.nextInt(characters.length())));
        }

        String loginData = loginDataBuilder.toString();
        LoginDto loginDto = new LoginDto();
        loginDto.setBsnOrEmail(loginData);
        loginDto.setPassword("password");

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            loginService.login(loginDto);
        });
        assertEquals(Constants.INVALID_CREDENTIALS_MESSAGE, exception.getMessage());
        verify(passwordRepository, never()).findById(any());
        verify(userRepository, never()).findById(any());
        verify(sessionService, never()).createSession(any());
    }
}