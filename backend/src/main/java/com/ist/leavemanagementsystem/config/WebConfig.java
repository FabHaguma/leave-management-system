package com.ist.leavemanagementsystem.config;

// import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Apply CORS to all API endpoints
                .allowedOrigins("http://localhost:3000") // Replace with your frontend's URL
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    // @Bean
    // public WebMvcConfigurer corsConfigurer() {
    // return new WebMvcConfigurer() {
    // @Override
    // public void addCorsMappings(CorsRegistry registry) {
    // registry.addMapping("/**") // Allow all endpoints
    // .allowedOrigins("http://localhost:3000") // Allow requests from this origin
    // .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allowed HTTP
    // methods
    // .allowedHeaders("*") // Allow all headers
    // .allowCredentials(true); // Allow cookies or credentials
    // }
    // };
    // }
}