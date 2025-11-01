package com.aliro2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador encargado únicamente de mostrar la página de login.
 * La gestión del proceso de autenticación (éxito o fallo) es manejada
 * por Spring Security según la configuración en SecurityConfig.
 */
@Controller
public class LoginController {

    /**
     * Mapea la petición GET /login para mostrar la vista del formulario.
     * @return El nombre de la plantilla de Thymeleaf a renderizar ("login").
     */
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

}
