package com.project.health_analytics.model;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.sql.Date;

/**
 * Model class for User
 * Contains all attributes of User
 */
@Entity
@Table(name = "user")
public class User {

    // Attributes
    @Id
    @Column(name = "bsn", nullable = false, unique = true)
    private Integer bsn;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "date_of_birth", nullable = false)
    private Date dateOfBirth;

    @Column(name = "user_name", nullable = false, unique = true, length = 16)
    private String userName;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false)
    private UserType userType;

    @Column(name = "profile_picture", nullable = false)
    private String profilePicture;

    @Column(name = "create_time", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createTime;

    @Column(name = "last_login")
    private Timestamp lastLogin;

    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @Column(name = "address", nullable = false, columnDefinition = "TINYTEXT")
    private String address;

    @Column(name = "country_of_residence", nullable = false)
    private String countryOfResidence;

    @Column(name = "insurance_policy", nullable = false)
    private String insurancePolicy;

    @Column(name = "marital_status")
    private String maritalStatus;

    @Column(name = "ice_contact_phone", nullable = false, length = 20)
    private String iceContactPhone;

    @Column(name = "ice_contact_name", nullable = false)
    private String iceContactName;

    // No-Argument Constructor
    public User() {
    }

    // Parameterized constructor
    public User(Integer bsn, String firstName, String middleName, String lastName, Date dateOfBirth, String userName,
            UserType userType, String profilePicture, Timestamp createTime, Timestamp lastLogin, String phoneNumber,
            String address, String countryOfResidence, String insurancePolicy, String maritalStatus,
            String iceContactPhone,
            String iceContactName) {
        this.bsn = bsn;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.userName = userName;
        this.userType = userType;
        this.profilePicture = profilePicture;
        this.createTime = createTime;
        this.lastLogin = lastLogin;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.countryOfResidence = countryOfResidence;
        this.insurancePolicy = insurancePolicy;
        this.maritalStatus = maritalStatus;
        this.iceContactPhone = iceContactPhone;
        this.iceContactName = iceContactName;
    }

    // Getters and Setters
    public Integer getBsn() {
        return bsn;
    }

    public void setBsn(Integer bsn) {
        this.bsn = bsn;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Timestamp lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getaddress() {
        return address;
    }

    public void setaddress(String address) {
        this.address = address;
    }

    public String getCountryOfResidence() {
        return countryOfResidence;
    }

    public void setCountryOfResidence(String countryOfResidence) {
        this.countryOfResidence = countryOfResidence;
    }

    public String getInsurancePolicy() {
        return insurancePolicy;
    }

    public void setInsurancePolicy(String insurancePolicy) {
        this.insurancePolicy = insurancePolicy;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getIceContactPhone() {
        return iceContactPhone;
    }

    public void setIceContactPhone(String iceContactPhone) {
        this.iceContactPhone = iceContactPhone;
    }

    public String getIceContactName() {
        return iceContactName;
    }

    public void setIceContactName(String iceContactName) {
        this.iceContactName = iceContactName;
    }
}