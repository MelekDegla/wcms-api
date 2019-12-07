package com.wecode.entity;

import com.wecode.entity.util.Request;

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
    private Integer status = 0;

    @ManyToOne
    @JoinColumn(name ="user_id")
    private User user;

    public Authorization(int id, Date dateRequest, LocalDate date, LocalTime beginhour, LocalTime endhour, String reason, Integer status, User user) {
        super(id, dateRequest);
        this.date = date;
        this.beginhour = beginhour;
        this.endhour = endhour;
        this.reason = reason;
        this.status = status;
        this.user = user;
    }

    public Authorization(int id, Date dateRequest) {
        super(id, dateRequest);
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
