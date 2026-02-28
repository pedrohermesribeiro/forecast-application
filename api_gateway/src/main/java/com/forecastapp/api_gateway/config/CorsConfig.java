// package com.forecastapp.api_gateway.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.web.reactive.config.CorsRegistry;
// //import org.springframework.web.servlet.config.annotation.CorsRegistry;
// //import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
// import org.springframework.web.reactive.config.WebFluxConfigurer;


// @Configuration
// public class CorsConfig {

//     @Bean
//     public WebFluxConfigurer corsConfigurer() {
//         return new WebFluxConfigurer() {
//             public void addCorsMappings(CorsRegistry registry) {
//                 registry.addMapping("/**")  // aplica para TODAS as rotas (inclui /roles)
//                         .allowedOrigins("https://forecast-frontend-gm1d.onrender.com")  // URL EXATA do frontend
//                         .allowedOrigins("*")  // para teste rápido (NÃO use em produção!)
//                         .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
//                         .allowedHeaders("Content-Type", "Authorization", "Accept")
//                         .allowCredentials(true)  // se usar cookies ou auth com credentials
//                         .maxAge(3600);  // cache do preflight por 1 hora
//             }
//         };
//     }

// }