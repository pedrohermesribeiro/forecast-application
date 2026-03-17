package com.forecastapp.auth_service.dto;

public class HomeResponseDTO {
    private String email;
    private String username;
    private boolean isAdmin;
    private String message;
    private boolean success = true;

    public HomeResponseDTO() {}

    public HomeResponseDTO(String email, String username, boolean isAdmin, String message) {
        this.email = email;
        this.username = username;
        this.isAdmin = isAdmin;
        this.message = message;
    }

    // Getters e Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public boolean isAdmin() { return isAdmin; }
    public void setAdmin(boolean isAdmin) { this.isAdmin = isAdmin; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
}