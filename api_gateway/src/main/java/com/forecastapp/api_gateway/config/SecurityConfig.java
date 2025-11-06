package com.forecastapp.api_gateway.config;

/*
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService users() {

        UserDetails user = User.builder()
            .username("admin")
            .password("{noop}1234") // senha sem criptografia, só para teste
            .roles("USER")
            .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/chat", "/actuator/**").permitAll()
                        .pathMatchers("/ai/**").permitAll() // libera acesso ao chatbot sem login
                        .pathMatchers("/auth/login").permitAll() // Adiciona a permissão para o endpoint de login
                        .anyExchange().authenticated()
                )
                .build();
    }


}

*/