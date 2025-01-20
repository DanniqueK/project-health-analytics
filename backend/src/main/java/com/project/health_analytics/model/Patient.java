package com.project.health_analytics.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * Model class for Patient
 * Contains all attributes of Patient
 */
@Entity
@Table(name = "patient")
public class Patient {

    // Attributes
    @Id
    @Column(name = "bsn", nullable = false)
    private Integer bsn;

    @Column(name = "allergies", columnDefinition = "TEXT")
    private String allergies;

    @Column(name = "medical_history", columnDefinition = "TEXT")
    private String medicalHistory;

    @Enumerated(EnumType.STRING)
    @Column(name = "blood_type")
    private BloodType bloodType;

    @Column(name = "height", nullable = false)
    private Integer height;

    @Column(name = "weight", nullable = false)
    private BigDecimal weight;

    @Column(name = "current_medication", columnDefinition = "TEXT")
    private String currentMedication;

    @Column(name = "smoking_status", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    private Boolean smokingStatus;

    @Column(name = "alcohol_consumption", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    private Boolean alcoholConsumption;

    @Column(name = "drug_consumption", columnDefinition = "TEXT")
    private String drugConsumption;

    @Column(name = "exercise_routine", columnDefinition = "TEXT")
    private String exerciseRoutine;

    @Column(name = "primary_physician", nullable = false)
    private String primaryPhysician;

    @Column(name = "date_of_registration", nullable = false, columnDefinition = "DATE DEFAULT CURRENT_DATE")
    private Date dateOfRegistration;

    @Column(name = "family_history", columnDefinition = "TEXT")
    private String familyHistory;

    // No-Argument Constructor
    public Patient() {
    }

    // Parameterized Constructor
    public Patient(Integer bsn, String allergies, String medicalHistory, BloodType bloodType, Integer height,
            BigDecimal weight,
            String currentMedication, Boolean smokingStatus, Boolean alcoholConsumption, String drugConsumption,
            String exerciseRoutine, String primaryPhysician, Date dateOfRegistration, String familyHistory) {
        this.bsn = bsn;
        this.allergies = allergies;
        this.medicalHistory = medicalHistory;
        this.bloodType = bloodType;
        this.height = height;
        this.weight = weight;
        this.currentMedication = currentMedication;
        this.smokingStatus = smokingStatus;
        this.alcoholConsumption = alcoholConsumption;
        this.drugConsumption = drugConsumption;
        this.exerciseRoutine = exerciseRoutine;
        this.primaryPhysician = primaryPhysician;
        this.dateOfRegistration = dateOfRegistration;
        this.familyHistory = familyHistory;
    }

    // Getters and Setters
    public Integer getBsn() {
        return bsn;
    }

    public void setBsn(Integer bsn) {
        this.bsn = bsn;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getCurrentMedication() {
        return currentMedication;
    }

    public void setCurrentMedication(String currentMedication) {
        this.currentMedication = currentMedication;
    }

    public Boolean getSmokingStatus() {
        return smokingStatus;
    }

    public void setSmokingStatus(Boolean smokingStatus) {
        this.smokingStatus = smokingStatus;
    }

    public Boolean getAlcoholConsumption() {
        return alcoholConsumption;
    }

    public void setAlcoholConsumption(Boolean alcoholConsumption) {
        this.alcoholConsumption = alcoholConsumption;
    }

    public String getDrugConsumption() {
        return drugConsumption;
    }

    public void setDrugConsumption(String drugConsumption) {
        this.drugConsumption = drugConsumption;
    }

    public String getExerciseRoutine() {
        return exerciseRoutine;
    }

    public void setExerciseRoutine(String exerciseRoutine) {
        this.exerciseRoutine = exerciseRoutine;
    }

    public String getPrimaryPhysician() {
        return primaryPhysician;
    }

    public void setPrimaryPhysician(String primaryPhysician) {
        this.primaryPhysician = primaryPhysician;
    }

    public Date getDateOfRegistration() {
        return dateOfRegistration;
    }

    public void setDateOfRegistration(Date dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
    }

    public String getFamilyHistory() {
        return familyHistory;
    }

    public void setFamilyHistory(String familyHistory) {
        this.familyHistory = familyHistory;
    }
}