package com.project.health_analytics.dto;

/**
 * Login Data Transfer Object:
 * This class is used to transfer login data from the client to the server.
 * It contains the user's BSN (Burger Service Number) and password.
 * 
 */
public class LoginDto {
    private String bsnOrEmail;
    private String password;

    // Getters and Setters
    public String getBsnOrEmail() {
        return bsnOrEmail;
    }

    public void setBsnOrEmail(String bsnOrEmail) {
        this.bsnOrEmail = bsnOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Check if the user is trying to login with an email address
     * 
     * @return true if the user is trying to login with an email address, false
     *         otherwise
     */
    public boolean isEmail() {
        return bsnOrEmail != null && bsnOrEmail.contains("@");
    }

    /**
     * Check if the user is trying to login with a BSN
     * 
     * @return true if the user is trying to login with a BSN, false otherwise
     */
    public boolean isBsn() {
        try {
            Integer.parseInt(bsnOrEmail);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}