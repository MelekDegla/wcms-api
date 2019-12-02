package com.wecode.entity.dto;

import com.wecode.entity.Holiday;

import java.util.List;

public class UserDto {
    private Long id;
    private String username;
    private String password;
    private long salary;
    private String birthdate;
    private String address;
    private Long leaveBalance;
    private String cin;
    private String email;
    private List<String> roles;
   private List<Holiday> holidays;

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

   public List<Holiday> getHolidays() {
        return holidays;
    }

    public void setHolidays(List<Holiday> holidays) {
        this.holidays = holidays;
    }
}
