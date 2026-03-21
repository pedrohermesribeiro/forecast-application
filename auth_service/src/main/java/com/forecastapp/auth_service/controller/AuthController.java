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
    public ResponseEntity<HomeResponseDTO> getHomeInfo(@RequestHeader(value = "Authorization", required = false) String authHeader) {

        HomeResponseDTO response = new HomeResponseDTO();

        // if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        //     response.setSuccess(false);
        //     response.setMessage("Token não encontrado ou inválido");
        //     return ResponseEntity.status(401).body(response);
        // }
        //String hash = HashUtil.sha256(this.emailUsuario);
        String token = jwtTokenUtil.generateToken(this.emailUsuario);

        try {
            // Valida o token (usando o util que você já tem)
            if (!jwtTokenUtil.validateToken(token)) {
                throw new Exception("Token inválido");
            }

            // Como o token atual é um hash, buscamos o usuário pelo UserClient (funciona!)
            // (se quiser, podemos melhorar o JWT depois para colocar email direto no token)
            UserDTO user = userClient.findByEmail01("exemplo@email.com").getBody(); // ← temporário

            // TODO: depois vamos pegar o email do token de forma correta
            response.setEmail(this.emailUsuario);
            response.setUsername(user.getUsername() != null ? user.getUsername() : user.getEmail());
            response.setAdmin(user.getRoles().stream().anyMatch(r -> r.getName().contains("ADMIN")));
            response.setMessage("Bem-vindo ao Forecast Application!");
            response.setSuccess(true);
            System.out.println("➡️ Chamando URL response: 1" + response.getEmail());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Token inválido ou expirado");
            return ResponseEntity.status(401).body(response);
        }
    }



}