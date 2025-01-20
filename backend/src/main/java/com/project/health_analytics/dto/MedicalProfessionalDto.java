package com.project.health_analytics.dto;

import java.math.BigDecimal;
import java.sql.Date;

public class MedicalProfessionalDto {

    private Integer bsn;
    private Long big;
    private Short yearOfExperience;
    private String degreeAndCertification;
    private Date licenseExpiryDate;
    private String languageSpoken;
    private String workSchedule;
    private BigDecimal consulationFee;
    private String availabilityStatus;
    private String contactEmail;
    private String officePhone;
    private BigDecimal reviewRating;
    private String specialty;
    private Integer affiliatedClinic;

    // No-Argument Constructor
    public MedicalProfessionalDto() {
    }

    // Parameterized Constructor
    public MedicalProfessionalDto(Integer bsn, Long big, Short yearOfExperience, String degreeAndCertification,
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

    public Long getBig() {
        return big;
    }

    public void setBig(Long big) {
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