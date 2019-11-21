package com.wecode.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "user_project")
@AssociationOverrides({
        @AssociationOverride(name = "primaryKey.user",
                joinColumns = @JoinColumn(name = "user_id")),
        @AssociationOverride(name = "primaryKey.project",
                joinColumns = @JoinColumn(name = "project_id")) })
public class UserProject  {
    @JsonIgnore
    private UserProjectId primaryKey = new UserProjectId();

    private boolean isManager ;

    @EmbeddedId
    public UserProjectId getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(UserProjectId primaryKey) {
        this.primaryKey = primaryKey;
    }

    @Transient
    @JsonIgnoreProperties("userProjects")
    public Project getProject() {
        return getPrimaryKey().getProject();
    }
    public void setProject(Project project) {
        getPrimaryKey().setProject(project);
    }
    @Transient
    @JsonIgnoreProperties("userProjects")
    public User getUser() {
        return getPrimaryKey().getUser();
    }

    public void setUser(User user) {
        getPrimaryKey().setUser(user);
    }

    public boolean isManager() {
        return isManager;
    }

    public void setManager(boolean manager) {
        isManager = manager;
    }




}
