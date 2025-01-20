package com.project.health_analytics.model;

import jakarta.persistence.*;
import java.sql.Date;

/**
 * Model class for HealthAnalyticsReport
 * Contains all attributes of HealthAnalyticsReport
 */
@Entity
@Table(name = "health_analytics_report")
public class HealthAnalyticsReport {

    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id", nullable = false)
    private Integer reportId;

    @Column(name = "patient_bsn", nullable = false)
    private Integer patientBsn;

    @Column(name = "date_of_generation", nullable = false)
    private Date dateOfGeneration;

    @Column(name = "summary", nullable = false, columnDefinition = "TINYTEXT")
    private String summary;

    @Column(name = "health_risk_prediction", columnDefinition = "TEXT")
    private String healthRiskPrediction;

    @Column(name = "recommendation", columnDefinition = "TEXT")
    private String recommendation;

    // No-Argument Constructor
    public HealthAnalyticsReport() {
    }

    // Parameterized Constructor
    public HealthAnalyticsReport(Integer reportId, Integer patientBsn, Date dateOfGeneration, String summary,
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