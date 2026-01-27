package com.forecastapp.auth_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

//import com.forecastapp.auth_service.security.JwtTokenUtil;
import com.forecastapp.auth_service.userClient.UserClient;
import com.forecastapp.auth_service.model.LoginRequest;
import com.forecastapp.auth_service.model.UserDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    // @Autowired
    // private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserClient userClient;

    @Autowired
    private RestTemplate restTemplate;

    private Integer cont = 0;

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@Valid @RequestBody LoginRequest request) {
        System.out.println("➡️ Chamando URL request: " + request + " " + cont);
        cont ++;
        UserDTO user = userClient.findByEmail(request.getEmail()).getBody();
        System.out.println("➡️ Chamando URL request: 1" + user.getPassword());
        System.out.println("➡️ Chamando URL request: 2" + request.getPassword());
        if (!user.getPassword().toString().equals(request.getPassword().toString())) {
            return ResponseEntity.status(401).body(user);
        }
        System.out.println("➡️ Chamando URL request: 3" + request);
        //String token = jwtTokenUtil.generateToken(user.getBody().getEmail());
        return ResponseEntity.ok(user);
    }





}