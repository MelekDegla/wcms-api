package com.wecode.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity

public class Project {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id ;

    @NotNull
    @Size(min= 3, message = "Project Name Length Should Be at min 3 chars !")
    private String name ;

    @NotNull
    @Size(min= 3, message = "Project Description Length Should Be at Least 3 !")
    private String description ;

    @ManyToMany
    @JoinTable (
            name ="user_project" ,
            joinColumns = @JoinColumn(name= "project_id") ,
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonIgnoreProperties("Project")
    private List<User> users ;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    @JsonIgnoreProperties("project")
    private List<Task> tasks;

    public Project(String name, String description, List<User> users, List<Task> tasks) {
        this.name = name;
        this.description = description;
        this.users = users;
        this.tasks = tasks;
    }

    public Project() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", users=" + users +
                ", tasks=" + tasks +
                '}';
    }
}
