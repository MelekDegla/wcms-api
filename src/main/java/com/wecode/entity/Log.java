package com.wecode.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Log {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    private String action;
    private String name;
    private LocalDateTime creationDateTime;
    private String oldStatus;
    private String newStatus;

    @ManyToOne
    @JoinColumn(name = "task_id")
    @JsonIgnoreProperties("logs")
    private Task task;


    public Log(String action, String name, LocalDateTime creationDateTime, String oldStatus, String newStatus, Task task) {
        this.action = action;
        this.name = name;
        this.creationDateTime = creationDateTime;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
        this.task = task;
    }

    public Log() {
    }

    public String getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(String oldStatus) {
        this.oldStatus = oldStatus;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
