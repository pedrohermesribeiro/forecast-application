package com.forecastapp.user_service.controller;

import com.forecastapp.user_service.model.User;
import com.forecastapp.user_service.model.UserDTO;
//import com.forecastapp.user_service.model.UserDTO;
import com.forecastapp.user_service.service.UserService;
import com.forecastapp.user_service.util.HashUtil;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:8085")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Criar usuário
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        System.out.println("📩 PARÂMETRO CRIAÇÃO DE USUÁRIO: " + user.getRoles());
        String plainPassword = user.getPassword();
        user.setPassword(HashUtil.sha256(plainPassword));
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    // Listar todos os usuários
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Buscar usuário por ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        System.out.println("📩 PARÂMETRO RECEBIDO PELO USER_SERVICE-1: " + id + " OI");
        User user = userService.getUserById(1L).get();
        System.out.println("📩 PARÂMETRO RECEBIDO PELO USER_SERVICE-2: " + user.getEmail());
        return ResponseEntity.ok(user);
    }

    // Atualizar usuário
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User updatedUser) {
        Optional<User> user = userService.updateUser(id, updatedUser);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Deletar usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    /*@GetMapping("/search/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable("email") String email) {
        System.out.println("📩 PARÂMETRO RECEBIDO PELO USER_SERVICE: " + email);
        Optional<User> user = userService.getByEmail(email);
        return user.map(ResponseEntity::ok)
               .orElseGet(() -> ResponseEntity.notFound().build());
    }*/

   /*  @GetMapping("/search/{email}")
    public ResponseEntity<User> getUserByEmail(@RequestParam("email") String email) {
        System.out.println("📩 PARÂMETRO RECEBIDO PELO USER_SERVICE: " + email);
        Optional<User> user = userService.getByEmail(email);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }*/

    @GetMapping("/search")
    public ResponseEntity<UserDTO> getUserByEmail(@RequestParam("email") String email) {  // Mude para RequestParam
        System.out.println("📩 EMAIL RECEBIDO: " + email);
        User userOpt = userService.getByEmail(email).get();
        System.out.println("📩 EMAIL RECEBIDO-2: " + userOpt.getPassword());
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userOpt.getId());
        userDTO.setEmail(userOpt.getEmail());
        userDTO.setPassword(userOpt.getPassword());
        userDTO.setRoles(userOpt.getRoles());
        
        //if (userOpt.isPresent()) {
          //  return ResponseEntity.ok(userOpt.get());
        // } else {
        //     return ResponseEntity.notFound().build();  // Retorna 404 se não encontrado
        // }
        return ResponseEntity.ok(userDTO);
    }





}

