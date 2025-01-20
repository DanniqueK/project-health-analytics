package com.project.health_analytics.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * Model class for MedicalProfessional
 * Contains all attributes of MedicalProfessional
 */
@Entity
@Table(name = "medical_professional")
public class MedicalProfessional {

    // Attributes
    @Id
    @Column(name = "bsn", nullable = false)
    private Integer bsn;

    @Column(name = "big", nullable = false)
    private String big;

    @Column(name = "year_of_experience", nullable = false)
    private Short yearOfExperience;

    @Column(name = "degree_and_certification", nullable = false, columnDefinition = "TEXT")
    private String degreeAndCertification;

    @Column(name = "license_expiry_date", nullable = false)
    private Date licenseExpiryDate;

    @Column(name = "language_spoken", nullable = false, columnDefinition = "TINYTEXT")
    private String languageSpoken;

    @Column(name = "work_schedule", columnDefinition = "TEXT")
    private String workSchedule;

    @Column(name = "consulation_fee", nullable = false, precision = 8, scale = 2)
    private BigDecimal consulationFee;

    @Column(name = "availability_status", nullable = false, columnDefinition = "ENUM('Available','Busy','On Leave')")
    private String availabilityStatus;

    @Column(name = "contact_email", nullable = false, columnDefinition = "TINYTEXT")
    private String contactEmail;

    @Column(name = "office_phone", nullable = false, length = 20)
    private String officePhone;

    @Column(name = "review_rating", nullable = false, precision = 3, scale = 2)
    private BigDecimal reviewRating;

    @Column(name = "specialty", nullable = false, columnDefinition = "TEXT")
    private String specialty;

    @Column(name = "affiliated_clinic", nullable = false)
    private Integer affiliatedClinic;

    // No-Argument Constructor
    public MedicalProfessional() {
    }

    // Parameterized Constructor
    public MedicalProfessional(Integer bsn, String big, Short yearOfExperience, String degreeAndCertification,
            Date licenseExpiryDate, String languageSpoken, String workSchedule, BigDecimal consulationFee,
            String availabilityStatus, String contactEmail, String officePhone, BigDecimal reviewRating,
            String specialty, Integer affiliatedClinic) {
        this.bsn = bsn;
        this.big = big;
        this.yearOfExperience = yearOfExperience;
        this.degreeAndCertification = degreeAndCertification;
        this.licenseExpiryDate = licenseExpiryDate;
        this.languageSpoken = languageSpoken;
        this.workSchedule = workSchedule;
        this.consulationFee = consulationFee;
        this.availabilityStatus = availabilityStatus;
        this.contactEmail = contactEmail;
        this.officePhone = officePhone;
        this.reviewRating = reviewRating;
        this.specialty = specialty;
        this.affiliatedClinic = affiliatedClinic;
    }

    // Getters and Setters
    public Integer getBsn() {
        return bsn;
    }

    public void setBsn(Integer bsn) {
        this.bsn = bsn;
    }

    public String getBig() {
        return big;
    }

    public void setBig(String big) {
        this.big = big;
    }

    public Short getYearOfExperience() {
        return yearOfExperience;
    }

    public void setYearOfExperience(Short yearOfExperience) {
        this.yearOfExperience = yearOfExperience;
    }

    public String getDegreeAndCertification() {
        return degreeAndCertification;
    }

    public void setDegreeAndCertification(String degreeAndCertification) {
        this.degreeAndCertification = degreeAndCertification;
    }

    public Date getLicenseExpiryDate() {
        return licenseExpiryDate;
    }

    public void setLicenseExpiryDate(Date licenseExpiryDate) {
        this.licenseExpiryDate = licenseExpiryDate;
    }

    public String getLanguageSpoken() {
        return languageSpoken;
    }

    public void setLanguageSpoken(String languageSpoken) {
        this.languageSpoken = languageSpoken;
    }

    public String getWorkSchedule() {
        return workSchedule;
    }

    public void setWorkSchedule(String workSchedule) {
        this.workSchedule = workSchedule;
    }

    public BigDecimal getConsulationFee() {
        return consulationFee;
    }

    public void setConsulationFee(BigDecimal consulationFee) {
        this.consulationFee = consulationFee;
    }

    public String getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(String availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public BigDecimal getReviewRating() {
        return reviewRating;
    }

    public void setReviewRating(BigDecimal reviewRating) {
        this.reviewRating = reviewRating;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public Integer getAffiliatedClinic() {
        return affiliatedClinic;
    }

    public void setAffiliatedClinic(Integer affiliatedClinic) {
        this.affiliatedClinic = affiliatedClinic;
    }
}