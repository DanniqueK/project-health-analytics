package com.project.health_analytics.dto;

public class UserBsnDto {
    private String bsn;

    // No-Argument Constructor
    public UserBsnDto() {
    }

    // Parameterized Constructor
    public UserBsnDto(String bsn) {
        this.bsn = bsn;
    }

    // Getters and Setters
    public String getBsn() {
        return bsn;
    }

    public void setBsn(String bsn) {
        this.bsn = bsn;
    }
}
