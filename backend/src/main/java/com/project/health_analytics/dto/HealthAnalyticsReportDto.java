package com.project.health_analytics.dto;

import java.sql.Date;

/**
 * Data Transfer Object for Health Analytics Report.
 * This class is used to transfer health analytics report data from the server
 * to the client.
 */
public class HealthAnalyticsReportDto {

    private Integer reportId;
    private Integer patientBsn;
    private Date dateOfGeneration;
    private String summary;
    private String healthRiskPrediction;
    private String recommendation;

    // No-Argument Constructor
    public HealthAnalyticsReportDto() {
    }

    // Parameterized Constructor
    public HealthAnalyticsReportDto(Integer reportId, Integer patientBsn, Date dateOfGeneration, String summary,
            String healthRiskPrediction, String recommendation) {
        this.reportId = reportId;
        this.patientBsn = patientBsn;
        this.dateOfGeneration = dateOfGeneration;
        this.summary = summary;
        this.healthRiskPrediction = healthRiskPrediction;
        this.recommendation = recommendation;
    }

    // Getters and Setters
    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public Integer getPatientBsn() {
        return patientBsn;
    }

    public void setPatientBsn(Integer patientBsn) {
        this.patientBsn = patientBsn;
    }

    public Date getDateOfGeneration() {
        return dateOfGeneration;
    }

    public void setDateOfGeneration(Date dateOfGeneration) {
        this.dateOfGeneration = dateOfGeneration;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getHealthRiskPrediction() {
        return healthRiskPrediction;
    }

    public void setHealthRiskPrediction(String healthRiskPrediction) {
        this.healthRiskPrediction = healthRiskPrediction;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }
}