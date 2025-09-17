package com.forecastapp.forecast_application.service;

import com.forecastapp.forecast_application.entity.User;
import com.forecastapp.forecast_application.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Criar usuário
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // Listar todos os usuários
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Buscar usuário por ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Atualizar usuário
    public Optional<User> updateUser(Long id, User updatedUser) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(updatedUser.getUsername());
            user.setEmail(updatedUser.getEmail());
            user.setPassword(updatedUser.getPassword()); // depois podemos adicionar criptografia
            user.setRoles(updatedUser.getRoles());
            return userRepository.save(user);
        });
    }

    // Deletar usuário
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}

