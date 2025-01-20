package com.project.health_analytics.model;

// i was feeling fancy here
/**
 * Enum representing the different types of users in the system.
 * 
 * <p>
 * This enum contains all possible user types that can be assigned to a user.
 * </p>
 * 
 * <ul>
 * <li>{@link #patient} - Represents a patient user.</li>
 * <li>{@link #medical_professional} - Represents a medical professional
 * user.</li>
 * <li>{@link #dentist} - Represents a dentist user.</li>
 * <li>{@link #admin} - Represents an admin user.</li>
 * </ul>
 */
public enum UserType {
    /**
     * Represents a patient user.
     */
    patient,

    /**
     * Represents a medical professional user.
     */
    medical_professional,

    /**
     * Represents a dentist user.
     */
    dentist,

    /**
     * Represents an admin user.
     */
    admin
}