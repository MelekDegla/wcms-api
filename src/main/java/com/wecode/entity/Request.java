package com.wecode.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dateRequest;
    private int status;
    private String rejectionReason;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Request() {
    }

    public Request(Date dateRequest, int status, User user) {
        this.dateRequest = dateRequest;
        this.status = status;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateRequest() {
        return dateRequest;
    }

    public void setDateRequest(Date dateRequest) {
        this.dateRequest = dateRequest;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", dateRequest=" + dateRequest +
                ", status=" + status +
                ", rejectionReason='" + rejectionReason + '\'' +
                ", user=" + user +
                '}';
    }
}