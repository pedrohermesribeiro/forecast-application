package com.forecastapp.auth_service.dto;

public class LoginResponseDTO {
    String token;

    public LoginResponseDTO(){

    }

    public String getToken(){
        return this.token;
    }

    public void setToken(String token){
        this.token = token;
    }

}
