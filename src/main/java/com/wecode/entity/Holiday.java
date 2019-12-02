package com.wecode.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

@Entity
public class Holiday {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;


    private String startDate;


    private String endDate;

    private int isValidated = 0;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("holidays")
    private User user;

    public Holiday() {}

    public Holiday( String startDate,  String endDate, User user, int isValidated) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.user = user;
        this.isValidated = isValidated;
    }

    public Holiday( String startDate,  String endDate, User user) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.user = user;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getIsValidated() {
        return isValidated;
    }

    public void setIsValidated(int isValidated) {
        this.isValidated = isValidated;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
