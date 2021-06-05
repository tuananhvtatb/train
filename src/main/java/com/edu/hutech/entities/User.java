package com.edu.hutech.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User extends BaseEntity implements UserDetails {

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();

    @Column(name="account")
    private String account;


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return account;
    }

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Attendance> attendanceList = new ArrayList<>();

    @Column(nullable = true, length = 64)
    private String photos;


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public List<Role> getRoles() {
        return roles;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    @OneToOne(mappedBy = "user")
    private Trainer trainer;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
