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
    /*@PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
    	System.out.println("Requisição recebida: " + request);
        // Aqui você validaria o usuário (mock por enquanto)
        if ("admin".equals(request.getUsername()) && "1234".equals(request.getPassword())) {
            String token = jwtTokenUtil.generateToken(request.getUsername());
            //return ResponseEntity.ok("Login realizado com sucesso. Token: MOCK123");
            return ResponseEntity.ok(Map.of("token", token));
        }
        return ResponseEntity.status(401).body("Usuário ou senha inválidos");
    }*/

          /*  public ResponseEntity<UserDTO> findByEmail(String email) {
        Long id = 1L;
        
            // try (Socket socket = new Socket("localhost", 8080)) {
            // // connection logic
            // } catch (java.net.ConnectException e) {
            // // handle the exception
            // }
         //ip = '192.168.0.6';
        //Socket clientSocket = new Socket(192.168.0.6, 5000);

        String baseUrl = "http://localhost:8083/users/{id}";

        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                        .queryParam("id", id)
                         .toUriString();

        
        
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("email", email)  // Automatically encodes '@' to '%40'
                .toUriString();

                System.out.println("➡️ Chamando URL: " + baseUrl);
        ResponseEntity<UserDTO> entity = new RestTemplate().getForEntity(url, UserDTO.class,id);
        //System.out.println("➡️ Chamando URL: " + baseUrl + " " + entity.getBody());
        return ResponseEntity.ok(entity.getBody());
    }*/

    public ResponseEntity<UserDTO> findByEmail(String email) {
    Long id = 1L;  // This seems like a placeholder; see notes below
    
    //String baseUrl = "http://localhost:8083/users/{id}";  // Use service name and internal port

    String baseUrl = "http://user-service:8083/users/search";  // Use service name and internal port

    String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
             .queryParam("email", email)  // Automatically encodes '@' to '%40'
             .toUriString();
    // String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
    //                 .buildAndExpand(id)  // Use buildAndExpand for path variables
    //                 .toUriString();

    System.out.println("➡️ Chamando URL: " + url);
    ResponseEntity<UserDTO> entity = new RestTemplate().getForEntity(url, UserDTO.class);
    //System.out.println("➡️ Chamando URL: " + url + " " + entity.getBody());


        // MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
        // parts.add("email", email);

        //UserDTO entity = restTemplate.getForObject(url, UserDTO.class);
        System.out.println("➡️ Chamando URL: 5" + entity);
        //UserDTO userDTO = new UserDTO(id);
    return ResponseEntity.ok(entity.getBody());
}

    /*
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest request) {
        
        
        ResponseEntity<UserDTO> user = findByEmail(request.getEmail());
        System.out.println("➡️ Chamando URL: " + user);

        if (user == null) {
            return ResponseEntity.status(401).body("Usuário não encontrado");
        }

        if (!user.getBody().getPassword().equals(request.getPassword())) {
            return ResponseEntity.status(401).body("Senha inválida");
        }

        //String token = jwtTokenUtil.generateToken(user.getUsername());

        //return ResponseEntity.ok(Map.of("token", token));

        return ResponseEntity.ok("token");
    }*/


@PostMapping("/login")
public ResponseEntity<UserDTO> login(@Valid @RequestBody LoginRequest request) {
    System.out.println("➡️ Chamando URL request: " + request + " " + cont);
    cont ++;
    UserDTO user = findByEmail(request.getEmail()).getBody();
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