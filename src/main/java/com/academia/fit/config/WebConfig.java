package com.academia.fit.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuração global de CORS para toda a aplicação.
 * Permite requisições do frontend React (localhost:5173).
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")                    // Aplica CORS em todas as rotas /api/*
                .allowedOrigins("http://localhost:5173")  // Frontend React
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                .allowedHeaders("*")                      // Permite todos os headers
                .allowCredentials(true)                   // Permite cookies (se necessário)
                .maxAge(3600);                            // Cache de preflight (1 hora)
    }
}