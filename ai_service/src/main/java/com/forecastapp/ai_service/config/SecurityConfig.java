package com.forecastapp.ai_service.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {

    /*@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // desabilita CSRF para Angular
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/ai/**").permitAll() // libera tudo em /ai
                .anyRequest().permitAll()              // libera o resto também (temporário)
            )
            .formLogin(form -> form.disable())         // desliga formulário de login padrão
            .httpBasic(basic -> basic.disable());      // desliga basic auth

        return http.build();
    }*/
    
}
