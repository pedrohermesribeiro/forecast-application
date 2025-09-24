package com.forecastapp.auth_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.forecastapp.auth_service.model.LoginRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest request) {
    	System.out.println("Requisição recebida: " + request);
        // Aqui você validaria o usuário (mock por enquanto)
        if ("admin".equals(request.getUsername()) && "1234".equals(request.getPassword())) {
            return ResponseEntity.ok("Login realizado com sucesso. Token: MOCK123");
        }
        return ResponseEntity.status(401).body("Usuário ou senha inválidos");
    }
}