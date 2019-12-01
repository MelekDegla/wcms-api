package com.wecode.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wecode.entity.Task;
import com.wecode.entity.User;
import com.wecode.entity.UserProject;

import java.util.ArrayList;
import java.util.List;

public class ProjectDto {
    private String name ;
    private String description ;
    private  List<User> users = new ArrayList<>();
    private List<Task> tasks;
    private float estimation;

    public float getEstimation() {
        int done = (int) tasks.stream().filter(task -> task.getStatus() == 4).count();
        return done * 100 / tasks.size();
    }

    public void setEstimation(float estimation) {
        this.estimation = estimation;
    }

    @JsonIgnore
    private List<UserProject> userProjects;

    public void setUserProjects(List<UserProject> userProjects) {
        this.userProjects = userProjects;
    }

    public ProjectDto(String name, String description, List<User> users, List<Task> tasks) {
        this.name = name;
        this.description = description;
        this.users = users;
        this.tasks = tasks;
    }
    public ProjectDto() {
        super();
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
        userProjects.forEach(up->this.users.add(up.getUser()));
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
}

