package com.aliro2.controller;
import com.aliro2.model.Usuario;
import com.aliro2.repository.CentroRepository;
import com.aliro2.repository.UsuarioRepository;
import com.aliro2.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    // Muestra la página de login
    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // Devuelve el nombre del archivo HTML: login.html
    }

    // Procesa el envío del formulario de login
    @PostMapping("/login")
    public String processLogin(@RequestParam String usuDni,
                               @RequestParam String usuClave,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {

        // Llamamos al método del servicio que contiene la lógica
        Optional<Usuario> usuarioValidado = usuarioService.validarUsuario(usuDni, usuClave);

        if (usuarioValidado.isPresent()) {
            session.setAttribute("usuarioLogueado", usuarioValidado.get());
            return "redirect:/panel";
        } else {
            redirectAttributes.addFlashAttribute("error", "DNI o contraseña incorrectos");
            return "redirect:/login";
        }
    }

    // Muestra la página del panel (protegida)
    @GetMapping("/panel")
    public String showPanel(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            // Si no hay nadie en la sesión, fuera de aquí
            return "redirect:/login";
        }

        model.addAttribute("usuario", usuario);
        return "panel"; // Devuelve el nombre del archivo HTML: panel.html
    }

    // Cierra la sesión del usuario
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Invalida la sesión
        return "redirect:/login";
    }
}
