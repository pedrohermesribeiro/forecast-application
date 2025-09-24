package com.forecastapp.auth_service.model;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
	
	@NotBlank(message = "Username is required")
    private String username;
	
	@NotBlank(message = "Password is required")
    private String password;
	
    private String email; // opcional

    // getters e setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
