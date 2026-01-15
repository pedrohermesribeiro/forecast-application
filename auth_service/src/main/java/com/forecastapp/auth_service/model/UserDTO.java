package com.forecastapp.auth_service.model;

import java.util.HashSet;
import java.util.Set;


// Import correto – ajuste conforme seu domínio
// Exemplo 1: se for Spring Security
// import org.springframework.security.core.GrantedAuthority;
// import java.util.Set<GrantedAuthority>;

// Exemplo 2: se for sua entidade customizada
//import com.forecastapp.auth_service.entity.Role;  // ou o pacote correto

public class UserDTO {

    private Long id;
    private String username;
    private String password;
    private String email;
    private Set<RoleDTO> roles = new HashSet<>();

    // Construtor vazio OBRIGATÓRIO para Jackson
    public UserDTO() {}

    public UserDTO(String email) {
        this.email = email;
    }

    public UserDTO(Long id) {
        this.id = id;
    }

    // Getters e Setters corretos
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<RoleDTO> getRoles() {          // ← corrigido: getRoles()
        return roles;
    }

    public void setRoles(Set<RoleDTO> roles) {
        this.roles = roles;
    }
}