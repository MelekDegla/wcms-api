package com.wecode.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Holiday extends Request {

    @NotNull
    private String startDate;

    @NotNull
    private String endDate;

    public Holiday(Date dateRequest,int status, User user) {
        super(dateRequest,status,user);
    }

    public Holiday(Date dateRequest, int status, String startDate,  String endDate, User user) {
        super(dateRequest,status,user);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Holiday() {
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




}
