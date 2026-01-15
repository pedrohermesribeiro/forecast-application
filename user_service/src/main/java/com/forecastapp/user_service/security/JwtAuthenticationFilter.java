// package com.forecastapp.user_service.security;

// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
// import org.springframework.stereotype.Component;
// import org.springframework.web.filter.OncePerRequestFilter;

// import java.io.IOException;

// @Component
// public class JwtAuthenticationFilter extends OncePerRequestFilter {

//     private final JwtTokenUtil jwtTokenUtil;

//     public JwtAuthenticationFilter(JwtTokenUtil jwtTokenUtil) {
//         this.jwtTokenUtil = jwtTokenUtil;
//     }

//     @Override
//     protected void doFilterInternal(HttpServletRequest request,
//                                     HttpServletResponse response,
//                                     FilterChain filterChain)
//             throws ServletException, IOException {

//         String header = request.getHeader("Authorization");
//         String token = null;

//         if (header != null && header.startsWith("Bearer ")) {
//             token = header.substring(7);
//         }

//         if (token != null && jwtTokenUtil.validateToken(token)) {
//             String username = jwtTokenUtil.getUsernameFromToken(token);

//             UsernamePasswordAuthenticationToken authentication =
//                     new UsernamePasswordAuthenticationToken(username, null, null);
//             authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

//             SecurityContextHolder.getContext().setAuthentication(authentication);
//         }

//         filterChain.doFilter(request, response);
//     }
// }

