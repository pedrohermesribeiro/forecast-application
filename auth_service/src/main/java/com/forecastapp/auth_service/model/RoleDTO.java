package com.forecastapp.auth_service.model;

//import jakarta.persistence.*;

public class RoleDTO {

    private Long id;

    private String name; // Ex.: ROLE_USER, ROLE_ADMIN

    // Construtores
    public RoleDTO() {}

    public RoleDTO(String name) {
        this.name = name;
    }

    // Getters e Setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}
