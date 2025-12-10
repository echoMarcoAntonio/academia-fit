package com.academia.fit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Desabilita CSRF de forma moderna
                .csrf(csrf -> csrf.disable())

                // Permite todas as requisições sem autenticação
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())

                // Desabilita login form
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
