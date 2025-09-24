package com.forecastapp.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forecastapp.user_service.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
