package com.aliro2.controller;

import com.aliro2.model.Usuario;
import com.aliro2.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador para la página principal o panel de control.
 * Su función es determinar qué vista de contenido mostrar
 * basándose en el rol (UsuTipo) del usuario autenticado.
 */
@Controller
public class PanelController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/panel")
    public String showPanel(Authentication authentication, Model model) {
        String dni = authentication.getName();
        Usuario usuario = usuarioRepository.findByUsuDni(dni)
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado"));

        String userRole = usuario.getUsuTipo().toUpperCase(); // Usamos toUpperCase() para unificar 'a' y 'A'
        String viewFragment;

        switch (userRole) {
            case "U":
            case "P":
                viewFragment = "panel-contents/usuario";
                break;
            case "T":
                viewFragment = "panel-contents/tecnico";
                break;
            // --- LÍNEA MODIFICADA ---
            case "A":
                viewFragment = "panel-contents/admin"; // Apunta al nuevo fragmento
                break;
            // -------------------------
            case "Z":
            case "Y":
                viewFragment = "panel-contents/superusuario";
                break;
            default:
                viewFragment = "panel-contents/default";
        }

        model.addAttribute("usuarioLogueado", usuario);
        model.addAttribute("view", viewFragment);

        return "layouts/layout";
    }
}