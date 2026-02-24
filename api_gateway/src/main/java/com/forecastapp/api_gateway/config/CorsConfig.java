package com.forecastapp.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.reactive.config.WebFluxConfigurer;


@Configuration
public class CorsConfig {

    @Bean
    public WebFluxConfigurer corsConfigurer() {
        return new WebFluxConfigurer() {
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOriginPatterns("*")           // ← MUDAR PARA ISSO
                        .allowedMethods("*")
                        .allowedHeaders("*")
                        .allowCredentials(true)               // pode continuar true
                        .maxAge(3600);
            }
        };
    }

}