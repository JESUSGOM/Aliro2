package com.aliro2.controller;

import com.aliro2.model.Usuario;
import com.aliro2.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Esta clase añade atributos automáticamente a todos los modelos
 * de todos los controladores.
 */
@ControllerAdvice
public class GlobalModelAttributesAdvice {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Este método se ejecuta en CADA petición y añade el objeto 'usuarioLogueado'
     * al modelo, haciéndolo disponible en TODAS las plantillas de Thymeleaf.
     */
    @ModelAttribute("usuarioLogueado")
    public Usuario addUsuarioLogueadoToModel(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            // Obtenemos el DNI del usuario logueado
            String dni = authentication.getName();

            // Buscamos el objeto Usuario completo y lo devolvemos
            return usuarioRepository.findByUsuDni(dni)
                    .orElse(null); // Devuelve null si no se encuentra (aunque no debería pasar si está logueado)
        }
        return null; // No hay usuario logueado
    }
}