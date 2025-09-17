package com.forecastapp.forecast_application.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String name; // Ex.: ROLE_USER, ROLE_ADMIN

    // Construtores
    public Role() {}

    public Role(String name) {
        this.name = name;
    }

    // Getters e Setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}
