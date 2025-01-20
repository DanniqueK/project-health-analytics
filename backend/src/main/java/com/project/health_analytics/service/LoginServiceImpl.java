package com.project.health_analytics.service;

import java.sql.Timestamp;
import java.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.health_analytics.dto.LoginDto;
import com.project.health_analytics.dto.SessionReturnDto;
import com.project.health_analytics.dto.UserTypeDto;
import com.project.health_analytics.model.MedicalProfessional;
import com.project.health_analytics.model.Password;
import com.project.health_analytics.model.User;
import com.project.health_analytics.repository.MedicalProfessionalRepository;
import com.project.health_analytics.repository.PasswordRepository;
import com.project.health_analytics.repository.UserRepository;
import com.project.health_analytics.util.Constants;
import com.project.health_analytics.util.PasswordUtil;

/**
 * Service implementation for handling user authentication and login operations.
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private PasswordRepository passwordRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MedicalProfessionalRepository medicalProfessionalRepository;
    @Autowired
    private SessionService sessionService;

    /**
     * Authenticates a user and creates a new session.
     *
     * @param loginDto The DTO containing login credentials
     * @return SessionReturnDto containing session information and user type
     * @throws RuntimeException if credentials are invalid or user not found
     */
    @Override
    public SessionReturnDto login(LoginDto loginDto) {
        validateLoginDto(loginDto);

        Integer bsn = getBsnFromDto(loginDto);
        validatePassword(bsn, loginDto.getPassword());
        User user = findAndValidateUser(bsn);

        updateLastLogin(user);
        SessionReturnDto sessionReturn = createAndValidateSession(user);
        addUserTypeToSession(sessionReturn, user);

        return sessionReturn;
    }

    /**
     * Validates the login DTO for null and empty values.
     *
     * @param loginDto The login DTO to validate
     * @throws RuntimeException if login DTO is invalid
     */
    private void validateLoginDto(LoginDto loginDto) {
        if (loginDto == null || loginDto.getBsnOrEmail() == null || loginDto.getBsnOrEmail().trim().isEmpty()) {
            throw new RuntimeException(Constants.INVALID_CREDENTIALS_MESSAGE);
        }
    }

    /**
     * Creates and validates a new session for the user.
     *
     * @param user The authenticated user
     * @return SessionReturnDto containing session information
     * @throws RuntimeException if session creation fails
     */
    private SessionReturnDto createAndValidateSession(User user) {
        SessionReturnDto sessionReturn = sessionService.createSession(user.getBsn());
        if (sessionReturn == null) {
            throw new RuntimeException(Constants.INTERNAL_SERVER_ERROR_MESSAGE);
        }
        return sessionReturn;
    }

    /**
     * Adds user type information to the session return DTO.
     *
     * @param sessionReturn The session return DTO
     * @param user          The authenticated user
     */
    private void addUserTypeToSession(SessionReturnDto sessionReturn, User user) {
        sessionReturn.setUserType(new UserTypeDto(user.getUserType()));
    }

    /**
     * Validates user password against stored credentials.
     *
     * @param bsn      The user's BSN
     * @param password The password to validate
     * @return Password entity if validation successful
     * @throws RuntimeException if password is invalid
     */
    private void validatePassword(Integer bsn, String password) {
        Password passwordEntity = passwordRepository.findById(bsn)
                .orElseThrow(() -> new RuntimeException(Constants.INVALID_CREDENTIALS_MESSAGE));

        if (!passwordEntity.isValid() || !PasswordUtil.verifyPassword(passwordEntity.getPassword(), password)) {
            throw new RuntimeException(Constants.INVALID_CREDENTIALS_MESSAGE);
        }
    }

    /**
     * Finds and validates a user by BSN.
     *
     * @param bsn The BSN to search for
     * @return User if found
     * @throws RuntimeException if user not found
     */
    private User findAndValidateUser(Integer bsn) {
        return userRepository.findById(bsn)
                .orElseThrow(() -> new RuntimeException(Constants.USER_NOT_FOUND + bsn));
    }

    /**
     * Updates the user's last login timestamp.
     *
     * @param user The user to update
     */
    private void updateLastLogin(User user) {
        user.setLastLogin(Timestamp.from(Instant.now()));
        userRepository.save(user);
    }

    /**
     * Extracts and validates BSN from login DTO.
     *
     * @param loginDto The login DTO containing BSN or email
     * @return The validated BSN
     * @throws RuntimeException if BSN cannot be extracted or is invalid
     */
    private Integer getBsnFromDto(LoginDto loginDto) {
        if (loginDto.isEmail()) {
            return getBsnFromEmail(loginDto.getBsnOrEmail());
        } else if (loginDto.isBsn()) {
            return parseBsn(loginDto.getBsnOrEmail());
        }
        throw new RuntimeException(Constants.INVALID_CREDENTIALS_MESSAGE);
    }

    /**
     * Retrieves BSN from email for medical professionals.
     *
     * @param email The email to search for
     * @return The associated BSN
     * @throws RuntimeException if email not found
     */
    private Integer getBsnFromEmail(String email) {
        return medicalProfessionalRepository.findByContactEmail(email)
                .map(MedicalProfessional::getBsn)
                .orElseThrow(() -> new RuntimeException(Constants.INVALID_CREDENTIALS_MESSAGE));
    }

    /**
     * Parses and validates BSN string.
     *
     * @param bsnString The BSN string to parse
     * @return The parsed BSN
     * @throws RuntimeException if BSN format is invalid
     */
    private Integer parseBsn(String bsnString) {
        try {
            return Integer.parseInt(bsnString);
        } catch (NumberFormatException e) {
            throw new RuntimeException(Constants.INVALID_CREDENTIALS_MESSAGE);
        }
    }
}