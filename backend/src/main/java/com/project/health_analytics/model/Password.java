package com.project.health_analytics.model;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "password")
public class Password {

    // Attributes
    @Id
    @Column(name = "bsn", nullable = false)
    private Integer bsn;

    @Column(name = "password", nullable = false, length = 128)
    private String password;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "expires_at", nullable = false)
    private Timestamp expiresAt;

    // No-Argument Constructor
    public Password() {
    }

    // Parameterized Constructor
    public Password(Integer bsn, String password, Boolean active, Timestamp expiresAt) {
        this.bsn = bsn;
        this.password = password;
        this.active = active;
        this.expiresAt = expiresAt;
    }

    // Getters and Setters
    public Integer getBsn() {
        return bsn;
    }

    public void setBsn(Integer bsn) {
        this.bsn = bsn;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Timestamp getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Timestamp expiresAt) {
        this.expiresAt = expiresAt;
    }

    // Methods

    /**
     * Checks if the password is active and not expired.
     * 
     * @return boolean - true if password is valid, false otherwise
     */
    public boolean isValid() {
        return this.getActive() && (this.getExpiresAt() == null
                || this.getExpiresAt().after(new Timestamp(System.currentTimeMillis())));

    }
}