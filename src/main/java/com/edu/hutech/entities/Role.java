package com.edu.hutech.entities;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class Role implements Serializable, GrantedAuthority {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;


    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "roles")
    private List<User> users = new ArrayList<>();

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public Role(int i, String roleTrainer) {
        this.id = i;
        this.name = roleTrainer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String getAuthority() {
        return this.name;
    }
}
