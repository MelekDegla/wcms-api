package com.wecode.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
  @ToString @Data
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
    @OneToMany(mappedBy = "primaryKey.project")
    @JsonIgnoreProperties({"project"})
    private List<UserProject> userProjects ;

//    @ManyToMany
//    @JoinTable (
//            name ="user_project" ,
//            joinColumns =    @JoinColumn(name= "project_id") ,
//            inverseJoinColumns = @JoinColumn(name = "user_id")
//    )
//    @JsonIgnoreProperties("Project")
//    private List<User> users ;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    @JsonIgnoreProperties({"project","userProjects"})
    private List<Task> tasks;

    public Project(){}

    public Project(String name, String description) {
        this.name = name;
        this.description = description;
    }

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

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<UserProject> getUserProjects() {
        return userProjects;
    }

    public void setUserProjects(List<UserProject> projects) {
        this.userProjects = projects;
    }

    public void addUserProjects(UserProject userProject) {
        this.userProjects.add(userProject);
    }




}
