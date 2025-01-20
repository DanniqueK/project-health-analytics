package com.project.health_analytics.dto;

public class SessionIdDto {
    private String sessionId;

    // No-Argument Constructor
    public SessionIdDto() {
    }

    // Parameterized Constructor
    public SessionIdDto(String sessionId) {
        this.sessionId = sessionId;
    }

    // Getters and Setters
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}