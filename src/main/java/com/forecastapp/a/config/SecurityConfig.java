package com.forecastapp.a.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // desabilita CSRF para facilitar teste via Postman
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/ai/**").permitAll() // libera acesso ao chatbot sem login
                .requestMatchers("/auth/login").permitAll() // Adiciona a permissão para o endpoint de login
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService users() {
        UserDetails user = User.builder()
            .username("admin")
            .password("{noop}1234") // senha sem criptografia, só para teste
            .roles("USER")
            .build();
        return new InMemoryUserDetailsManager(user);
    }
}