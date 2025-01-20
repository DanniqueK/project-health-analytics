package com.project.health_analytics.model;

import jakarta.persistence.*;
import java.sql.Timestamp;

/**
 * Model class for Session
 * Contains all attributes of Session
 */
@Entity
@Table(name = "session")
public class Session {

    // Attributes
    @Id
    @Column(name = "bsn", nullable = false)
    private Integer bsn;

    @Column(name = "session_id", nullable = false, length = 128)
    private String sessionId;

    @Column(name = "expiry_date", nullable = false)
    private Timestamp expiryDate;

    // No-Argument Constructor
    public Session() {
    }

    // Parameterized Constructor
    public Session(Integer bsn, String sessionId, Timestamp expiryDate) {
        this.bsn = bsn;
        this.sessionId = sessionId;
        this.expiryDate = expiryDate;
    }

    public Session(Integer bsn, String sessionId) {
        this.bsn = bsn;
        this.sessionId = sessionId;
    }

    // Getters and Setters
    public Integer getBsn() {
        return bsn;
    }

    public void setBsn(Integer bsn) {
        this.bsn = bsn;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Timestamp getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Timestamp expiryDate) {
        this.expiryDate = expiryDate;
    }
}