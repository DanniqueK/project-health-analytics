package com.project.health_analytics.dto;

import java.sql.Timestamp;
import java.sql.Date;

import com.project.health_analytics.model.UserType;

public class UserDto {
    // Fields
    private Integer bsn;
    private String firstName;
    private String middleName;
    private String lastName;
    private Date dateOfBirth;
    private String userName;
    private UserType userType;
    private String profilePicture;
    private Timestamp createTime;
    private Timestamp lastLogin;
    private String phoneNumber;
    private String address;
    private String countryOfResidence;
    private String insurancePolicy;
    private String maritalStatus;
    private String iceContactPhone;
    private String iceContactName;

    // Parameterized constructor
    public UserDto(Integer bsn, String firstName, String middleName, String lastName, Date dateOfBirth, String userName,
            UserType userType, String profilePicture, Timestamp createTime, Timestamp lastLogin, String phoneNumber,
            String address, String countryOfResidence, String insurancePolicy, String maritalStatus,
            String iceContactPhone, String iceContactName) {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
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