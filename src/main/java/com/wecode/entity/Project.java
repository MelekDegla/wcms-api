package com.wecode.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
 @NoArgsConstructor @ToString @Data
public class Project {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id ;
    private String name ;
    private String description ;
    @OneToMany(mappedBy = "primaryKey.project",
            cascade = CascadeType.ALL)
    @JsonIgnoreProperties("userProjects")
    private Set<UserProject> userProjects = new HashSet<UserProject>();

    @ManyToMany
    @JoinTable (
            name ="user_project" ,
            joinColumns =    @JoinColumn(name= "project_id") ,
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonIgnoreProperties("Project")
    private List<User> users ;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    @JsonIgnoreProperties("project")
    private List<Task> tasks;

    public Project(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Set<UserProject> getUserProjects() {
        return userProjects;
    }

    public void setUserProjects(Set<UserProject> projects) {
        this.userProjects = projects;
    }

    public void addUserProjects(UserProject userProject) {
        this.userProjects.add(userProject);
    }
}
