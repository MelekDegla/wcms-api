package com.wecode.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Time;
import java.time.LocalDate;

@Entity
public class Authorization {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id ;
    private LocalDate date ;
    private Time beginhour ;
    private  Time endhour ;
    private  String reason ;

    public Authorization() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Time getBeginhour() {
        return beginhour;
    }

    public void setBeginhour(Time beginhour) {
        this.beginhour = beginhour;
    }

    public Time getEndhour() {
        return endhour;
    }

    public void setEndhour(Time endhour) {
        this.endhour = endhour;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Authorization(LocalDate date, Time beginhour, Time endhour, String reason) {
        this.date = date;
        this.beginhour = beginhour;
        this.endhour = endhour;
        this.reason = reason;
    }
}
