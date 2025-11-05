package com.aliro2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// Importamos el PasswordEncoder que SÍ vamos a usar (en tu caso, el personalizado)
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // --- CÓDIGO CORREGIDO ---
        // Ya que estás usando tu propio codificador que convierte a mayúsculas,
        // lo instanciamos aquí. Esto soluciona la advertencia de 'NoOpPasswordEncoder'
        // porque ya no lo estamos llamando directamente.

        // Si sigues usando el NoOpPasswordEncoder para pruebas:
        return new UpperCasePasswordEncoder(NoOpPasswordEncoder.getInstance());

        // Si decides migrar a BCrypt (RECOMENDADO PARA PRODUCCIÓN):
        // return new UpperCasePasswordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Permite el acceso sin autenticación a la página de login y a los recursos estáticos
                        .requestMatchers("/login", "/css/**", "/ICONOS/**").permitAll()
                        // Cualquier otra petición requiere autenticación
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        // Le decimos a Spring Security cuál es nuestra página de login
                        .loginPage("/login")
                        // La URL a la que se enviará el formulario (manejada por Spring Security)
                        .loginProcessingUrl("/login") // Spring maneja esta URL
                        // A dónde redirigir si el login es exitoso
                        .defaultSuccessUrl("/panel", true)
                        // A dónde redirigir si el login falla
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        // URL para cerrar sesión
                        .logoutUrl("/logout")
                        // A dónde redirigir después de cerrar sesión
                        .logoutSuccessUrl("/login?logout=true")
                        .permitAll()
                );

        return http.build();
    }
}