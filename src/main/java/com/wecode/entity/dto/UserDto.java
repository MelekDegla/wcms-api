package com.wecode.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wecode.entity.UserProject;

import java.util.List;

public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private long salary;
    private String birthdate;
    private String address;
    private Long leaveBalance;
    private String cin;
    private String email;
    private String rib;
    private String facebook;
    private String instagram;
    private String linkedin;

    private List<String> roles;
    @JsonIgnore
    private List<UserProject> userProjects;

    public List<UserProject> getUserProjects() {
        return userProjects;
    }

    public void setUserProjects(List<UserProject> userProjects) {
        this.userProjects = userProjects;
    }




    public UserDto(Long id, String username, String password, long salary, String birthdate, String address, Long leaveBalance, String cin, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.salary = salary;
        this.birthdate = birthdate;
        this.address = address;
        this.leaveBalance = leaveBalance;
        this.cin = cin;
        this.email = email;
    }

    public UserDto(Long id, String firstName, String lastName, String username, String password, long salary, String birthdate, String address, Long leaveBalance, String cin, String email, String rib, String facebook, String instagram, String linkedin, List <String> roles, List <UserProject> userProjects) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.salary = salary;
        this.birthdate = birthdate;
        this.address = address;
        this.leaveBalance = leaveBalance;
        this.cin = cin;
        this.email = email;
        this.rib = rib;
        this.facebook = facebook;
        this.instagram = instagram;
        this.linkedin = linkedin;
        this.roles = roles;
        this.userProjects = userProjects;
    }

    public UserDto() {
    }

    public Long getId() {
        return id;
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

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
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

    public void setId(Long id) {
        this.id = id;
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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getRib() {
        return rib;
    }

    public void setRib(String rib) {
        this.rib = rib;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
