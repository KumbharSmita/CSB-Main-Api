//package com.example.accountapi.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests() // Updated method for Spring Security 6.1
//                .requestMatchers("/api/accounts").permitAll()  // Allow /api/accounts without authentication
//                .anyRequest().authenticated()
//                .and()
//                .httpBasic();  // Enable Basic Auth
//
//        // CSRF protection is enabled by default, no need to disable unless necessary
//        return http.build();
//    }
//}
