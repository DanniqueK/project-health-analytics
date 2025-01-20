package com.project.health_analytics.dto;

import java.sql.Timestamp;

public class SessionDto {

    private Integer bsn;
    private String sessionId;
    private Timestamp expiryDate;

    // No-Argument Constructor
    public SessionDto() {
    }

    // Parameterized Constructor
    public SessionDto(Integer bsn, String sessionId, Timestamp expiryDate) {
        this.bsn = bsn;
        this.sessionId = sessionId;
        this.expiryDate = expiryDate;
    }

    public SessionDto(String sessionId) {
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