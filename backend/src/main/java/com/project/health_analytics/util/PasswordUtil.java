package com.project.health_analytics.util;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

/**
 * Utility class for password hashing and verification.
 * Uses the Argon2 password hashing algorithm.
 * 
 * @method hashPassword - Hashes a plain text password using Argon2
 * @method verifyPassword - Verifies a plain text password against a hashed
 *         password
 */
public class PasswordUtil {

    private static final Argon2 argon2 = Argon2Factory.create();

    /**
     * Hashes a plain text password using Argon2.
     *
     * @param plainPassword the plain text password to hash
     * @return the hashed password
     */
    public static String hashPassword(String plainPassword) {
        // Parameters: t=1 iteration, p=4 lanes, m=2^21 (2 GiB of RAM)
        Integer iterations = 1;
        Integer memory = 1 << 21; // 2 GiB
        Integer parallelism = 4;

        return argon2.hash(iterations, memory, parallelism, plainPassword.toCharArray());
    }

    /**
     * Verifies a plain text password against a hashed password.
     *
     * @param hashedPassword the hashed password
     * @param plainPassword  the plain text password to verify
     * @return true if the password matches, false otherwise
     */
    public static boolean verifyPassword(String hashedPassword, String plainPassword) {
        if (plainPassword == null || hashedPassword == null) {
            return false;
        }
        boolean result = argon2.verify(hashedPassword, plainPassword.toCharArray());
        return result;
    }
}