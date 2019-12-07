package com.wecode.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wecode.entity.util.Request;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;

import static java.time.temporal.ChronoUnit.DAYS;

@Entity
public class Holiday extends Request {

    private String startDate;


    private String endDate;

    private int isValidated = 0;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("holidays")
    private User user;

    public Holiday(int id, Date dateRequest) {
        super(id, dateRequest);
    }

    public Holiday(int id, Date dateRequest, String startDate,  String endDate, User user, int isValidated) {
        super(id, dateRequest);
        this.startDate = startDate;
        this.endDate = endDate;
        this.user = user;
        this.isValidated = isValidated;
    }


    public Holiday(int id, Date dateRequest, String startDate, String endDate, User user) {
        super(id, dateRequest);
        this.startDate = startDate;
        this.endDate = endDate;
        this.user = user;
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
