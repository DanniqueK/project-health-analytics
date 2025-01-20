package com.project.health_analytics.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * Model class for HealthData
 * Contains all attributes of HealthData
 */
@Entity
@Table(name = "health_data")
public class HealthData {

    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "data_id", nullable = false)
    private Integer dataId;

    @Column(name = "patient_bsn", nullable = false)
    private Integer patientBsn;

    @Column(name = "date_of_collection", nullable = false)
    private Date dateOfCollection;

    @Column(name = "heart_rate")
    private Short heartRate;

    @Column(name = "blood_pressure")
    private Integer bloodPressure;

    @Column(name = "cholesterol_level")
    private Integer cholesterolLevel;

    @Column(name = "blood_sugar_level")
    private Integer bloodSugarLevel;

    @Column(name = "body_mass_index", precision = 5, scale = 2)
    private BigDecimal bodyMassIndex;

    @Column(name = "sleep_pattern", columnDefinition = "TEXT")
    private String sleepPattern;

    @Column(name = "step_count")
    private Integer stepCount;

    @Column(name = "caloric_intake")
    private Integer caloricIntake;

    @Column(name = "exercise_duration")
    private Short exerciseDuration;

    @Column(name = "mental_health_assessment", columnDefinition = "TEXT")
    private String mentalHealthAssessment;

    @Column(name = "dietary_habit", columnDefinition = "TEXT")
    private String dietaryHabit;

    @Column(name = "substance_history", columnDefinition = "TEXT")
    private String substanceHistory;

    // No-Argument Constructor
    public HealthData() {
    }

    // Parameterized Constructor
    public HealthData(Integer dataId, Integer patientBsn, Date dateOfCollection, Short heartRate, Integer bloodPressure,
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