package com.project.health_analytics.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PasswordUtilTest {

    @Test
    void testHashPassword() {
        System.out.println("Executing testHashPassword");
        // Given
        String plainPassword = "mySecurePassword";

        // When
        String hashedPassword = PasswordUtil.hashPassword(plainPassword);

        // Then
        assertNotNull(hashedPassword, "Hashed password should not be null");
        assertNotEquals(plainPassword, hashedPassword, "Hashed password should not be equal to the plain password");
    }

    @Test
    void testVerifyPassword() {
        System.out.println("Executing testVerifyPassword");
        // Given
        String plainPassword = "mySecurePassword";
        String hashedPassword = PasswordUtil.hashPassword(plainPassword);

        // When
        boolean isPasswordValid = PasswordUtil.verifyPassword(hashedPassword, plainPassword);

        // Then
        assertTrue(isPasswordValid, "Password should be valid");
    }

    @Test
    void testVerifyPasswordWithInvalidPassword() {
        System.out.println("Executing testVerifyPasswordWithInvalidPassword");
        // Given
        String plainPassword = "mySecurePassword";
        String hashedPassword = PasswordUtil.hashPassword(plainPassword);
        String invalidPassword = "invalidPassword";

        // When
        boolean isPasswordValid = PasswordUtil.verifyPassword(hashedPassword, invalidPassword);

        // Then
        assertFalse(isPasswordValid, "Password should be invalid");
    }

    @Test
    void testHashPasswordConsistency() {
        System.out.println("Executing testHashPasswordConsistency");
        // Given
        String plainPassword = "mySecurePassword";

        // When
        String hashedPassword1 = PasswordUtil.hashPassword(plainPassword);
        String hashedPassword2 = PasswordUtil.hashPassword(plainPassword);

        // Then
        assertNotEquals(hashedPassword1, hashedPassword2, "Hashed passwords should not be the same");
    }

    @Test
    void testVerifyPasswordWithNullPassword() {
        System.out.println("Executing testVerifyPasswordWithNullPassword");
        // Given
        String plainPassword = "mySecurePassword";
        String hashedPassword = PasswordUtil.hashPassword(plainPassword);

        // When
        boolean isPasswordValid = PasswordUtil.verifyPassword(hashedPassword, null);

        // Then
        assertFalse(isPasswordValid, "Password should be invalid when null is provided");
    }
}