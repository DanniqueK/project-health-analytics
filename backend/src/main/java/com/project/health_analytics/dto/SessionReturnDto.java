package com.project.health_analytics.dto;

public class SessionReturnDto {
    private String sessionId;
    private UserTypeDto userType;

    // No-Argument Constructor
    public SessionReturnDto() {
    }

    // Parameterized Constructor
    public SessionReturnDto(String sessionId, UserTypeDto userType) {
        this.sessionId = sessionId;
        this.userType = userType;
    }

    // Getters and Setters
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public UserTypeDto getUserType() {
        return userType;
    }

    public void setUserType(UserTypeDto userType) {
        this.userType = userType;
    }
}
