package com.aliro2.controller;

import com.aliro2.model.AperturaExtra;
import com.aliro2.model.Usuario;
import com.aliro2.repository.UsuarioRepository;
import com.aliro2.service.AperturaExtraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class AperturaExtraController {

    private final AperturaExtraService aperturaExtraService;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public AperturaExtraController(AperturaExtraService aperturaExtraService, UsuarioRepository usuarioRepository) {
        this.aperturaExtraService = aperturaExtraService;
        this.usuarioRepository = usuarioRepository;
    }

    // --- Métodos Auxiliares de Seguridad ---

    private Integer getCentroUsuario(Authentication authentication) {
        String dni = authentication.getName();
        return usuarioRepository.findByUsuDni(dni)
                .map(Usuario::getUsuCentro)
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado o sin centro asignado"));
    }

    // ========================================================================
    // 1. GESTIÓN DEL FORMULARIO (NUEVO Y EDITAR)
    // ========================================================================

    /**
     * MUESTRA EL FORMULARIO PARA REGISTRAR UNA NUEVA APERTURA
     * URL: /aperturas/nueva
     */
    @GetMapping("/aperturas/nueva")
    public String mostrarFormularioNuevo(Model model, Authentication authentication) {
        AperturaExtra apertura = new AperturaExtra();
        apertura.setAeCentro(getCentroUsuario(authentication)); // Asigna el centro

        model.addAttribute("apertura", apertura);
        model.addAttribute("pageTitle", "Registrar Apertura Extraordinaria");
        model.addAttribute("view", "vistas-formularios/form-apertura");
        return "layouts/layout";
    }

    /**
     * MUESTRA EL FORMULARIO PARA EDITAR UNA APERTURA EXISTENTE
     * URL: /aperturas/editar/{id}
     */
    @GetMapping("/aperturas/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Integer id, Model model, Authentication authentication) {
        Integer centroUsuario = getCentroUsuario(authentication);
        AperturaExtra apertura = aperturaExtraService.findByIdAndCentro(id, centroUsuario)
                .orElseThrow(() -> new IllegalArgumentException("ID de apertura inválido o acceso denegado"));

        model.addAttribute("apertura", apertura);
        model.addAttribute("pageTitle", "Editar Apertura Extraordinaria");
        model.addAttribute("view", "vistas-formularios/form-apertura");
        return "layouts/layout";
    }

    /**
     * GUARDA LA APERTURA (NUEVA O EDITADA)
     * URL: /aperturas/guardar
     */
    @PostMapping("/aperturas/guardar")
    public String guardarApertura(@ModelAttribute("apertura") AperturaExtra apertura, Authentication authentication) {
        Integer centroUsuario = getCentroUsuario(authentication);

        // Seguridad: Comprobar que el registro pertenece al centro del usuario
        if (!apertura.getAeCentro().equals(centroUsuario)) {
            return "redirect:/panel?error=accesoDenegado";
        }

        aperturaExtraService.save(apertura);
        return "redirect:/aperturas/informes"; // Redirige al informe
    }

    // ========================================================================
    // 2. INFORME Y ELIMINACIÓN
    // ========================================================================

    /**
     * MUESTRA EL INFORME HISTÓRICO DE APERTURAS
     * URL: /aperturas/informes
     */
    @GetMapping("/aperturas/informes")
    public String mostrarInforme(Model model, Authentication authentication,
                                 @RequestParam(name = "page", defaultValue = "0") int page,
                                 @RequestParam(name = "keyword", required = false) String keyword) {

        Integer centroUsuario = getCentroUsuario(authentication);
        Pageable pageable = PageRequest.of(page, 10); // 10 por página

        Page<AperturaExtra> aperturasPage = aperturaExtraService.findAllByCentro(centroUsuario, keyword, pageable);
        model.addAttribute("aperturasPage", aperturasPage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("baseURL", "/aperturas/informes"); // Para la paginación

        int totalPaginas = aperturasPage.getTotalPages();
        if (totalPaginas > 0) {
            List<Integer> numerosPagina = IntStream.rangeClosed(1, totalPaginas).map(i -> i - 1).boxed().collect(Collectors.toList());
            model.addAttribute("numerosPagina", numerosPagina);
        }

        model.addAttribute("pageTitle", "Informe de Aperturas Extraordinarias");
        model.addAttribute("view", "vistas-listados/list-aperturas-informe");
        return "layouts/layout";
    }

    /**
     * ELIMINA UN REGISTRO DE APERTURA
     * URL: /aperturas/eliminar/{id}
     */
    @GetMapping("/aperturas/eliminar/{id}")
    public String eliminarApertura(@PathVariable("id") Integer id, Authentication authentication) {
        Integer centroUsuario = getCentroUsuario(authentication);

        // Seguridad: Verificar que el registro pertenece al centro del usuario antes de borrar
        AperturaExtra apertura = aperturaExtraService.findByIdAndCentro(id, centroUsuario)
                .orElseThrow(() -> new IllegalArgumentException("ID de apertura inválido o acceso denegado"));

        aperturaExtraService.deleteById(apertura.getAeId());
        return "redirect:/aperturas/informes";
    }
}