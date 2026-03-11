package com.forecastapp.auth_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.forecastapp.auth_service.security.JwtTokenUtil;
import com.forecastapp.auth_service.userClient.UserClient;
import com.forecastapp.auth_service.util.HashUtil;
import com.forecastapp.auth_service.dto.LoginResponseDTO;
import com.forecastapp.auth_service.model.LoginRequest;
import com.forecastapp.auth_service.model.UserDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

      @Autowired
      private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserClient userClient;

    private Integer cont = 0;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequest request) {
        System.out.println("➡️ Chamando URL request: " + request + " " + cont);
        cont ++;
        UserDTO user = userClient.findByEmail(request.getEmail()).getBody();
        System.out.println("➡️ Chamando URL request: 1" + user.getPassword());
        System.out.println("➡️ Chamando URL request: 2" + request.getPassword());
        LoginResponseDTO responseDTO = new LoginResponseDTO();
        String hashedRequestPassword = HashUtil.sha256(request.getPassword()); // Hasheie a senha recebida
        if (!user.getPassword().toString().equals(hashedRequestPassword)) {
            return ResponseEntity.status(401).body(null);
        }
        System.out.println("➡️ Chamando URL request: 3" + request);
        String hash = HashUtil.sha256(user.getEmail() + request.getPassword());
        String token = jwtTokenUtil.generateToken(hash);
        responseDTO.setToken(token);

        return ResponseEntity.ok(responseDTO);
    }





}