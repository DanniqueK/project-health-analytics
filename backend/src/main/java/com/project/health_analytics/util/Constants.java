package com.project.health_analytics.util;

/**
 * Class containing all constants used in the application
 */
public class Constants {
    // Session related constants
    public static final long SESSION_EXPIRY_TIME = 300000000; // 30 minutes

    // Warning messages
    public static final String INVALID_CREDENTIALS_MESSAGE = "Invalid credentials";
    public static final String INTERNAL_SERVER_ERROR_MESSAGE = "An error occurred during operations";
    public static final String SESSION_NOT_FOUND = "Session not found for sessionId: ";
    public static final String SESSION_RETRIEVE_FAILURE = "Failed to retrieve the session with session ID: ";
    public static final String USER_NOT_FOUND = "User not found for bsn: ";
    public static final String BSN_NOT_FOUND = "BSN not found: ";
    public static final String INVALID_OR_MISSING_SESSION_ID = "Invalid or missing session ID";
    public static final String ACCESS_DENIED = "Access denied";
    public static final String LOGOUT_SUCCESSFUL_MESSAGE = "Logout successful";
    public static final String INVALID_BIG_NUMBER = "Invalid BIG number format. Must be exactly 11 digits.";
    public static final String DATABASE_ERROR = "Database operation failed";

}