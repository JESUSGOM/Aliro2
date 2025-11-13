package com.aliro2.controller;

import com.aliro2.dto.MenuGroupDto; // <- AÑADIDO
import com.aliro2.model.Usuario;
import com.aliro2.repository.UsuarioRepository;
import com.aliro2.service.MenuService; // <- AÑADIDO
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Collections; // <- AÑADIDO
import java.util.List;      // <- AÑADIDO

/**
 * Esta clase añade atributos automáticamente a todos los modelos
 * de todos los controladores.
 */
@ControllerAdvice
public class GlobalModelAttributesAdvice {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MenuService menuService; // <- AÑADIDO

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
                    .orElse(null);
        }
        return null; // No hay usuario logueado
    }

    /**
     * (NUEVO MÉTODO)
     * Este método se ejecuta en CADA petición y añade la lista del menú dinámico
     * ('menuGroups') al modelo.
     */
    @ModelAttribute("menuGroups")
    public List<MenuGroupDto> addMenuToModel(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {

            // Buscamos al usuario (similar a como lo hacemos arriba)
            String dni = authentication.getName();
            Usuario usuario = usuarioRepository.findByUsuDni(dni).orElse(null);

            if (usuario != null) {
                // Si el usuario existe, pedimos a MenuService que construya el menú
                // basado en su TIPO (Rol) y su CENTRO.
                return menuService.getDynamicMenuForUser(usuario.getUsuTipo(), usuario.getUsuCentro());
            }
        }
        // Si no hay usuario logueado, devuelve una lista vacía.
        return Collections.emptyList();
    }
}