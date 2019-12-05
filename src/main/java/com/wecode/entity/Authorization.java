package com.wecode.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Authorization {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id ;
    private LocalDate date ;
    private LocalTime beginhour ;
    private  LocalTime endhour ;
    private  String reason ;
    private Integer status = 0;

    @ManyToOne
    @JoinColumn(name ="user_id")
    private User user;

    public Authorization() {
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Authorization(LocalDate date, LocalTime beginhour, LocalTime endhour, String reason) {
        this.date = date;
        this.beginhour = beginhour;
        this.endhour = endhour;
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
