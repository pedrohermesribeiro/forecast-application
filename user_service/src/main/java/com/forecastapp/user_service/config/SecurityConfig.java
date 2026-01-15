//package com.forecastapp.user_service.config;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;

//import com.forecastapp.user_service.security.JwtAuthenticationFilter;

//import java.util.Arrays;


// @Configuration
// public class SecurityConfig {

   /*  @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {}); // habilita CORS

        return http.build();
    }*/

    //private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // @Autowired
    // public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
    //     this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    // }

    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    //     http
    //         .csrf(csrf -> csrf.disable())
    //         .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    //         .authorizeHttpRequests(users -> users
    //             .requestMatchers("/users/**", "/public/**").permitAll() // libera /ai/chat
    //             .anyRequest().permitAll()

    //             //.anyRequest().authenticated() // exige JWT nas demais
    //         );
    //         //.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    //     return http.build();
    // }

// @Bean
//     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

//         http
//             .csrf(csrf -> csrf.disable())
//             .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
//             .httpBasic(httpBasic -> httpBasic.disable())
//             .formLogin(form -> form.disable());

//         return http.build();
//     }

    //     @Bean
    // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    //     http
    //         .csrf(csrf -> csrf.disable())
    //         .formLogin(form -> form.disable())
    //         .httpBasic(basic -> basic.disable())
    //         .authorizeHttpRequests(users -> users
    //             .anyRequest().permitAll() // LIBERA TUDO NO AUTH SERVICE
    //         );

    //     return http.build();
    // }

    // @Bean
    // public CorsFilter corsFilter() {

    //     CorsConfiguration config = new CorsConfiguration();
    //     config.setAllowCredentials(true);
    //     config.setAllowedOrigins(Arrays.asList(
    //             "http://localhost:4200",
    //             "http://localhost:8085"
    //     ));
    //     config.setAllowedHeaders(Arrays.asList("*"));
    //     config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

    //     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    //     source.registerCorsConfiguration("/**", config);

    //     return new CorsFilter(source);
    // }

    // @Bean
    // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    //     http
    //         .csrf(csrf -> csrf.disable())
    //         .authorizeHttpRequests(auth -> auth
    //             .requestMatchers(
    //                 "/users/**",
    //                 "/users/search/**"
    //             ).permitAll()
    //             .anyRequest().permitAll()
    //         )
    //         .cors(Customizer.withDefaults());
    //     return http.build();
    // }







//}



