package com.project.health_analytics.dto;

import java.util.Date;

public class PatientDto {
    private Integer bsn;
    private String name;
    private Date dateOfBirth;

    public PatientDto(Integer bsn, String name, Date dateOfBirth) {
        this.bsn = bsn;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBsn() {
        return bsn;
    }

    public void setBsn(Integer bsn) {
        this.bsn = bsn;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}