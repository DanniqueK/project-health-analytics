package com.project.health_analytics.dto;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * Data Transfer Object for Health Data.
 * This class is used to transfer health data from the server to the client.
 */
public class HealthDataDto {

    private Integer dataId;
    private Integer patientBsn;
    private Date dateOfCollection;
    private Short heartRate;
    private Integer bloodPressure;
    private Integer cholesterolLevel;
    private Integer bloodSugarLevel;
    private BigDecimal bodyMassIndex;
    private String sleepPattern;
    private Integer stepCount;
    private Integer caloricIntake;
    private Short exerciseDuration;
    private String mentalHealthAssessment;
    private String dietaryHabit;
    private String substanceHistory;

    // No-Argument Constructor
    public HealthDataDto() {
    }

    // Parameterized Constructor
    public HealthDataDto(Integer dataId, Integer patientBsn, Date dateOfCollection, Short heartRate,
            Integer bloodPressure,
            Integer cholesterolLevel, Integer bloodSugarLevel, BigDecimal bodyMassIndex, String sleepPattern,
            Integer stepCount, Integer caloricIntake, Short exerciseDuration, String mentalHealthAssessment,
            String dietaryHabit, String substanceHistory) {
        this.dataId = dataId;
        this.patientBsn = patientBsn;
        this.dateOfCollection = dateOfCollection;
        this.heartRate = heartRate;
        this.bloodPressure = bloodPressure;
        this.cholesterolLevel = cholesterolLevel;
        this.bloodSugarLevel = bloodSugarLevel;
        this.bodyMassIndex = bodyMassIndex;
        this.sleepPattern = sleepPattern;
        this.stepCount = stepCount;
        this.caloricIntake = caloricIntake;
        this.exerciseDuration = exerciseDuration;
        this.mentalHealthAssessment = mentalHealthAssessment;
        this.dietaryHabit = dietaryHabit;
        this.substanceHistory = substanceHistory;
    }

    // Getters and Setters
    public Integer getDataId() {
        return dataId;
    }

    public void setDataId(Integer dataId) {
        this.dataId = dataId;
    }

    public Integer getPatientBsn() {
        return patientBsn;
    }

    public void setPatientBsn(Integer patientBsn) {
        this.patientBsn = patientBsn;
    }

    public Date getDateOfCollection() {
        return dateOfCollection;
    }

    public void setDateOfCollection(Date dateOfCollection) {
        this.dateOfCollection = dateOfCollection;
    }

    public Short getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(Short heartRate) {
        this.heartRate = heartRate;
    }

    public Integer getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(Integer bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public Integer getCholesterolLevel() {
        return cholesterolLevel;
    }

    public void setCholesterolLevel(Integer cholesterolLevel) {
        this.cholesterolLevel = cholesterolLevel;
    }

    public Integer getBloodSugarLevel() {
        return bloodSugarLevel;
    }

    public void setBloodSugarLevel(Integer bloodSugarLevel) {
        this.bloodSugarLevel = bloodSugarLevel;
    }

    public BigDecimal getBodyMassIndex() {
        return bodyMassIndex;
    }

    public void setBodyMassIndex(BigDecimal bodyMassIndex) {
        this.bodyMassIndex = bodyMassIndex;
    }

    public String getSleepPattern() {
        return sleepPattern;
    }

    public void setSleepPattern(String sleepPattern) {
        this.sleepPattern = sleepPattern;
    }

    public Integer getStepCount() {
        return stepCount;
    }

    public void setStepCount(Integer stepCount) {
        this.stepCount = stepCount;
    }

    public Integer getCaloricIntake() {
        return caloricIntake;
    }

    public void setCaloricIntake(Integer caloricIntake) {
        this.caloricIntake = caloricIntake;
    }

    public Short getExerciseDuration() {
        return exerciseDuration;
    }

    public void setExerciseDuration(Short exerciseDuration) {
        this.exerciseDuration = exerciseDuration;
    }

    public String getMentalHealthAssessment() {
        return mentalHealthAssessment;
    }

    public void setMentalHealthAssessment(String mentalHealthAssessment) {
        this.mentalHealthAssessment = mentalHealthAssessment;
    }

    public String getDietaryHabit() {
        return dietaryHabit;
    }

    public void setDietaryHabit(String dietaryHabit) {
        this.dietaryHabit = dietaryHabit;
    }

    public String getSubstanceHistory() {
        return substanceHistory;
    }

    public void setSubstanceHistory(String substanceHistory) {
        this.substanceHistory = substanceHistory;
    }
}