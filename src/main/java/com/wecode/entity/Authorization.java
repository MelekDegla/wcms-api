package com.wecode.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Entity
public class Authorization extends Request {
    private LocalDate date ;
    private LocalTime beginhour ;
    private  LocalTime endhour ;
    private  String reason ;

    public Authorization(Date dateRequest,int status, LocalDate date, LocalTime beginhour, LocalTime endhour, String reason, User user) {
        super(dateRequest,status, user);
        this.date = date;
        this.beginhour = beginhour;
        this.endhour = endhour;
        this.reason = reason;
    }

    public Authorization(int status, Date dateRequest, User user) {
        super(dateRequest,status, user);
    }

    public Authorization() {
    }

    public Authorization(Date dateRequest, int status, User user, LocalDate date) {
        super(dateRequest, status, user);
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }


    public LocalTime getBeginhour() {
        return beginhour;
    }

    public void setBeginhour(LocalTime beginhour) {
        this.beginhour = beginhour;
    }

    public LocalTime getEndhour() {
        return endhour;
    }

    public void setEndhour(LocalTime endhour) {
        this.endhour = endhour;
    }

}
