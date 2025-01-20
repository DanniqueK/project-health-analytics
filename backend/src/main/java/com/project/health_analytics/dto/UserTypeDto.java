package com.project.health_analytics.dto;

import com.project.health_analytics.model.UserType;

public class UserTypeDto {
    private UserType userType;

    // No-Argument Constructor
    public UserTypeDto() {
    }

    // Parameterized Constructor
    public UserTypeDto(UserType userType) {
        this.userType = userType;
    }

    // Getters and Setters
    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
