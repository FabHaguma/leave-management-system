package com.ist.leavemanagementsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        // @Bean
        // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
        // Exception {
        // http.cors() // Enable CORS
        // .and()
        // .csrf().disable() // Disable CSRF for simplicity (not recommended for
        // production)
        // .authorizeRequests()
        // .requestMatchers("/api/**").permitAll() // Allow all requests to /api/**
        // .anyRequest().authenticated();
        // return http.build();
        // }

        @Bean
        @Profile("dev")
        public SecurityFilterChain developmentSecurityFilterChain(HttpSecurity http)
                        throws Exception {
                http
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/api/**").permitAll()
                                                .anyRequest().permitAll())
                                .csrf(AbstractHttpConfigurer::disable);
                return http.build();
        }

        @Bean
        @Profile("!dev")
        public SecurityFilterChain productionSecurityFilterChain(HttpSecurity http,
                        CustomOAuth2UserService customOAuth2UserService) throws Exception {
                http
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/api/health").permitAll()
                                                .requestMatchers("/api/**").authenticated()
                                                .anyRequest().permitAll())
                                .oauth2Login(oauth -> oauth
                                                .userInfoEndpoint(user -> user
                                                                .userService(customOAuth2UserService)));
                return http.build();
        }
}