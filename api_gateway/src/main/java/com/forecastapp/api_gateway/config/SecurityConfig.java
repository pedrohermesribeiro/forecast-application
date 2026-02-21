package com.forecastapp.api_gateway.config;

//import java.beans.Customizer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // @Bean
    // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    //     http
    //         .csrf(csrf -> csrf.disable())
    //         .authorizeHttpRequests(auth -> auth
    //             .requestMatchers(
    //                 "/auth/**",
    //                 "/users/**",
    //                 "/users/search/**",
    //                 "/ai/**",
    //                 "/chat/**",
    //                 "/actuator/**"
    //             ).permitAll()
    //             .anyRequest().permitAll()
    //         )
    //         .cors(Customizer.withDefaults());
    //     return http.build();
    // }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()) // libera tudo
            .authorizeHttpRequests(auth -> auth.requestMatchers("/chat", "/public/**").permitAll())
            .authorizeHttpRequests(auth -> auth.requestMatchers("/users", "/public/**").permitAll())
            .authorizeHttpRequests(auth -> auth.requestMatchers("/roles", "/public/**").permitAll())
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }



    

/*
@Bean
public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http){

        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(ex ->
                        ex
                                .pathMatchers("/actuator/**").permitAll()
                                .pathMatchers("/ai/**").permitAll() // gateway deve repassar
                                .anyExchange().permitAll()
                );

        return http.build();
    }*/


}



