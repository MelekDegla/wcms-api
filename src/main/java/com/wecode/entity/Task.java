package com.wecode.entity;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
public class Task {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    private String label;
    private String description;

    private Integer status;
@ManyToOne
@JsonIgnoreProperties("tasks")
private Project project ;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Task(String label, String description, Integer status, Project project) {
        this.label = label;
        this.description = description;
        this.status = status;
        this.project = project;
    }

    public Task() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}