package com.forecastapp.user_service.model;

import java.util.HashSet;
import java.util.Set;

public class UserDTO {

    private Long id;
    private String username;
    private String password;
    private String email;
    private Set<Role> roles = new HashSet<>();
    
    public Long getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getEmail() {
        return email;
    }
    public Set<Role> getRoles() {
        return roles;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    

}
