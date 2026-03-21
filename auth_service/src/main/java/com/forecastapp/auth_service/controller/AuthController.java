package com.forecastapp.auth_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.forecastapp.auth_service.security.JwtTokenUtil;
import com.forecastapp.auth_service.userClient.UserClient;
import com.forecastapp.auth_service.util.HashUtil;
import com.forecastapp.auth_service.dto.HomeResponseDTO;
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

    private String emailUsuario;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequest request) {
        System.out.println("➡️ Chamando URL request: " + request + " " + cont);
        cont ++;
        UserDTO user = userClient.findByEmail(request.getEmail()).getBody();
        System.out.println("➡️ Chamando URL request: 1" + user.getPassword());
        System.out.println("➡️ Chamando URL request: 2" + request.getPassword());
        this.emailUsuario = user.getEmail();
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

@GetMapping("/home")
public ResponseEntity<HomeResponseDTO> getHomeInfo(
        @RequestHeader(value = "Authorization", required = false) String authHeader) {

    HomeResponseDTO response = new HomeResponseDTO();
    System.out.println("➡️ Chamando URL response: 0" + authHeader);

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        response.setSuccess(false);
        response.setMessage("Token não encontrado ou formato inválido");
        return ResponseEntity.status(401).body(response);
    }

    String token = authHeader.substring(7);
    System.out.println("➡️ Chamando URL token: 1" + token.substring(0, 20) + "...");

    try {
        if (!jwtTokenUtil.validateToken(token)) {
            response.setSuccess(false);
            response.setMessage("Token inválido ou expirado");
            return ResponseEntity.status(401).body(response);
        }

        // Extrai o identificador (deve ser email ou hash, dependendo do que você gravou)
        String identifier = jwtTokenUtil.getUsernameFromToken(token);  // implemente esse método se não tiver

        System.out.println("➡️ Identificador extraído do token: " + identifier);

        UserDTO user = userClient.findByEmail(identifier).getBody();

        if (user == null) {
            response.setSuccess(false);
            response.setMessage("Usuário não encontrado");
            return ResponseEntity.status(404).body(response);
        }

        response.setEmail(user.getEmail());
        response.setUsername(user.getUsername() != null ? user.getUsername() : user.getEmail());
        response.setAdmin(user.getRoles().stream().anyMatch(r -> r.getName().contains("ADMIN")));
        response.setMessage("Bem-vindo ao Forecast Application!");
        response.setSuccess(true);

        System.out.println("➡️ Chamando URL response: 2 " + response.getEmail());
        System.out.println("➡️ Usuário encontrado: " + user.getEmail());

        return ResponseEntity.ok(response);

    } catch (Exception e) {
        System.out.println("❌ Erro no /home: " + e.getMessage());
        e.printStackTrace();  // para ver stacktrace no log do Render
        response.setSuccess(false);
        response.setMessage("Erro ao processar requisição: " + e.getMessage());
        return ResponseEntity.status(401).body(response);
    }
}



}