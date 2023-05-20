package com.example.mobileappserver.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = Integer.MAX_VALUE)
    private String name;

    @Column(name = "password", length = 4)
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "registered")
    private Boolean registered;

    @Column(name = "device_token",nullable = false, length = Integer.MAX_VALUE)
    private String deviceToken;

    @Column(name = "gender", nullable = false, length = 1)
    private String gender;

    @Column(name = "active", nullable = false)
    private Boolean active = false;

    @Column(name = "ability")
    private Integer ability;

    @Column(name = "working_years", nullable = false)
    private Integer workingYears;

    @Column(name = "is_admin")
    private Boolean isAdmin;

    public User(String name, String password, String email, Boolean registered, String deviceToken, String gender, Boolean active, Integer ability, Integer workingYears, Boolean isAdmin) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.registered = registered;
        this.deviceToken = deviceToken;
        this.gender = gender;
        this.active = active;
        this.ability = ability;
        this.workingYears = workingYears;
        this.isAdmin = isAdmin;
    }

    public Boolean getRegistered() {
        return registered;
    }

    public void setRegistered(Boolean registered) {
        this.registered = registered;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }




    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getAbility() {
        return ability;
    }

    public void setAbility(Integer ability) {
        this.ability = ability;
    }

    public Integer getWorkingYears() {
        return workingYears;
    }

    public void setWorkingYears(Integer workingYears) {
        this.workingYears = workingYears;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

}