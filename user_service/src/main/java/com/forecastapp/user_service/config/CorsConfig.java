package com.forecastapp.user_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
        @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")  // aplica para TODAS as rotas (inclui /roles)
                        .allowedOrigins("https://forecast-frontend-gm1d.onrender.com")  // URL EXATA do frontend
                        .allowedOrigins("*")  // para teste rápido (NÃO use em produção!)
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                        .allowedHeaders("Content-Type", "Authorization", "Accept")
                        .allowCredentials(true)  // se usar cookies ou auth com credentials
                        .maxAge(3600);  // cache do preflight por 1 hora
            }
        };
    }
}



    //     @Bean
    // public WebMvcConfigurer corsConfigurer() {
    //     return new WebMvcConfigurer() {
    //         @Override
    //         public void addCorsMappings(CorsRegistry registry) {
    //             registry.addMapping("/**")
    //                     .allowedOrigins("http://localhost:4200") // Permita o seu frontend local
    //                     .allowedOrigins("http://localhost:8085")
    //                     .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
    //                     .allowedHeaders("*")
    //                     .allowCredentials(true);
    //         }
    //     };
    // }

//}