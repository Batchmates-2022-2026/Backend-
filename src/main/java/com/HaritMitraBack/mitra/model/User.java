package com.HaritMitraBack.mitra.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String city;
    private boolean weatherAlertEnabled;
    private boolean activityAlertEnabled;
    private Boolean rainAlert;
    private Boolean stormAlert;
    private Boolean heatAlert;
    private Boolean monsoonAlert;
    private String name;
    private String phone;
    private String username;


    // 🔐 Verification flags
    private Boolean emailVerified = false;
    private Boolean phoneVerified = false;

    // 🔢 OTP storage
    private String emailOtp;
    private String phoneOtp;
//   private String cropType;
//   private String farmsize;
//   private String soilType;
    @Column(unique = true)
    private String email;

    private String password;

    @Column(nullable = false)
    private String role = "USER";
    private Long lastActiveTime;
    private String fcmToken;
    public String getFcmToken()
    { return fcmToken; }
    public void setFcmToken(String fcmToken) { this.fcmToken = fcmToken; }


    public void setLastActiveTime(Long lastActiveTime) { this.lastActiveTime = lastActiveTime; }
    public Long getLastActiveTime() {
        return lastActiveTime;
    }


    // getters & setters


    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public Boolean getPhoneVerified() {
        return phoneVerified;
    }

    public void setPhoneVerified(Boolean phoneVerified) {
        this.phoneVerified = phoneVerified;
    }

    public String getEmailOtp() {
        return emailOtp;
    }

    public void setEmailOtp(String emailOtp) {
        this.emailOtp = emailOtp;
    }

    public String getPhoneOtp() {
        return phoneOtp;
    }

    public void setPhoneOtp(String phoneOtp) {
        this.phoneOtp = phoneOtp;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getRainAlert() {
        return rainAlert;
    }

    public void setRainAlert(Boolean rainAlert) {
        this.rainAlert = rainAlert;
    }

    public Boolean getStormAlert() {
        return stormAlert;
    }

    public void setStormAlert(Boolean stormAlert) {
        this.stormAlert = stormAlert;
    }

    public boolean isWeatherAlertEnabled() {
        return weatherAlertEnabled;
    }

    public void setWeatherAlertEnabled(boolean weatherAlertEnabled) {
        this.weatherAlertEnabled = weatherAlertEnabled;
    }

    public Boolean getHeatAlert() {
        return heatAlert;
    }

    public void setHeatAlert(Boolean heatAlert) {
        this.heatAlert = heatAlert;
    }

    public Boolean getMonsoonAlert() {
        return monsoonAlert;
    }

    public void setMonsoonAlert(Boolean monsoonAlert) {
        this.monsoonAlert = monsoonAlert;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isActivityAlertEnabled() {
        return activityAlertEnabled;
    }

    public void setActivityAlertEnabled(boolean activityAlertEnabled) {
        this.activityAlertEnabled = activityAlertEnabled;
    }

    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}