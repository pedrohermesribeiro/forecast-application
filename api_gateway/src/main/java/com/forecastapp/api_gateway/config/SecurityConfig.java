/*package com.forecastapp.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/auth/**",
                    "/users/**",
                    "/users/search/**",
                    "/ai/**",
                    "/chat/**",
                    "/actuator/**"
                ).permitAll()
                .anyRequest().permitAll()
            )
            .cors(Customizer.withDefaults());
        return http.build();
    }

    /*@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()) // libera tudo
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


//}



