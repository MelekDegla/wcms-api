package com.wecode.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private String description;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "USER_ROLES", joinColumns = {
            @JoinColumn(name = "ROLE_ID") }, inverseJoinColumns = {
            @JoinColumn(name = "USER_ID") })
    private List<Role> users = new ArrayList<>();

    public Role( String s, String s1) {

        name = s;
        description= s1;
    }

    public Role() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public List<Role> getUsers() {
        return users;
    }

    public void setUsers(List<Role> users) {
        this.users = users;
    }
}
