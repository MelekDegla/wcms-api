package com.wecode.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    @NotNull
    @Size(min = 3, message = "Username Length Should Be At Least 3!")
    private String username;
    @Column
    @JsonIgnore
    @NotNull
    @Size(min = 6, message = "Password Length Should Be At Least 3!")
    private String password;
    @Column
    @NotNull
    private long salary;
    @Column
    @NotNull
    private LocalDate birthdate;

    private String address;

    private Long leaveBalance;
    @Column(unique = true)
    @NotNull
    private String cin;
    @Column(unique = true)
    private String email;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "USER_ROLES", joinColumns = {
            @JoinColumn(name = "USER_ID") }, inverseJoinColumns = {
            @JoinColumn(name = "ROLE_ID") })
    @JsonIgnoreProperties("users")
    private List<Role> roles = new ArrayList<>();
    @ManyToMany
    @JoinTable (
            name ="user_project" ,
            joinColumns = @JoinColumn(name= "user_id") ,
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    @JsonIgnoreProperties("user")
    private List<Project> projects ;
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }


    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getLeaveBalance() {
        return leaveBalance;
    }

    public void setLeaveBalance(Long leaveBalance) {
        this.leaveBalance = leaveBalance;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
}