// package com.forecastapp.api_gateway.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.web.SecurityFilterChain;

// @Configuration
// public class SecurityConfig {

//     @Bean
//     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//         http
//             .csrf(csrf -> csrf.disable()) // desabilita CSRF para Angular
//             .authorizeHttpRequests(auth -> auth
//                 .requestMatchers("/ai/**").permitAll() // libera tudo em /ai
//                 .anyRequest().permitAll()              // libera o resto também (temporário)
//             )
//             .formLogin(form -> form.disable())         // desliga formulário de login padrão
//             .httpBasic(basic -> basic.disable());      // desliga basic auth

//         return http.build();
//     }
// }



