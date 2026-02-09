package com.example.taskmanager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Todos los endpoints
                // Orígenes permitidos (donde está tu frontend)
                .allowedOrigins(
                    "http://localhost:3000",     // React
                    "http://localhost:4200",     // Angular
                    "http://localhost:5173",     // Vite
                    "http://localhost:8081",     // Vue
                    "http://127.0.0.1:5500",     // Live Server (VS Code)
                    "https://tu-frontend.vercel.app"  // Producción (cambia esto)
                )
                // Métodos HTTP permitidos
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                // Headers permitidos
                .allowedHeaders("*")
                // Permitir enviar credenciales (cookies, Authorization header)
                .allowCredentials(true)
                // Tiempo que el navegador cachea la respuesta CORS
                .maxAge(3600);
    }
}