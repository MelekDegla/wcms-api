package com.wecode.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public class UserProjectId implements Serializable {
    @JsonIgnoreProperties("userProjects")
    private User user;
    @JsonIgnoreProperties("userProjects")

    private Project project;
    @ManyToOne(cascade = CascadeType.ALL)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    @ManyToOne(cascade = CascadeType.ALL)
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
