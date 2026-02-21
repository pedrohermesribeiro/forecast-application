// package com.forecastapp.api_gateway.config;

// import org.springframework.web.cors.CorsConfiguration;
// //import java.beans.Customizer;
// import org.springframework.web.cors.CorsConfigurationSource;
// import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

// import java.util.List;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.web.SecurityFilterChain;

// @Configuration
// @EnableWebSecurity
// public class SecurityConfig {

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

// @Bean
// public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//     http
//         // Desabilita CSRF (ok para APIs stateless/JWT)
//         .csrf(csrf -> csrf.disable())

//         // Integra CORS (essencial para resolver seu erro de CORS no frontend Angular)
//         // Use um CorsConfigurationSource bean separado (veja abaixo)
//         .cors(cors -> cors.configurationSource(corsConfigurationSource()))

//         // ÚNICA chamada a authorizeHttpRequests - combine todas as regras aqui
//         .authorizeHttpRequests(auth -> auth
//             // Libera endpoints públicos (inclui /public/**, /chat, /users, /roles se forem públicos)
//             .requestMatchers("/chat", "/users", "/roles", "/public/**").permitAll()

//             // Se quiser mais regras específicas, adicione aqui (ex: autenticados)
//             // .requestMatchers("/admin/**").hasRole("ADMIN")
//             // .requestMatchers("/api/private/**").authenticated()

//             // O que sobrar: pode ser .authenticated() ou .permitAll()
//             // Para seu caso de teste: .anyRequest().permitAll() (libera tudo)
//             .anyRequest().permitAll()
//         )

//         // Sessão stateless (bom para JWT/API)
//         .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

//     return http.build();
// }


// @Bean
// public CorsConfigurationSource corsConfigurationSource() {
//     CorsConfiguration configuration = new CorsConfiguration();
    
//     // Origem exata do seu frontend no Render (sem barra no final!)
//     configuration.setAllowedOrigins(List.of("https://forecast-frontend-gm1d.onrender.com"));
    
//     // Para teste rápido (não use em produção!):
//     // configuration.setAllowedOrigins(List.of("*"));
    
//     configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
//     configuration.setAllowedHeaders(List.of("Content-Type", "Authorization", "Accept"));
//     configuration.setAllowCredentials(true);  // Se usar cookies ou auth com credentials
//     configuration.setMaxAge(3600L);  // Cache do preflight por 1 hora

//     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//     source.registerCorsConfiguration("/**", configuration);  // Aplica para todas as rotas
//     return source;
// }
    

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


// }



