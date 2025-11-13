package com.aliro2.controller;

import com.aliro2.model.Paquete;
import com.aliro2.model.Retposto;
import com.aliro2.model.Usuario;
import com.aliro2.repository.RetpostoRepository;
import com.aliro2.repository.UsuarioRepository;
import com.aliro2.service.PaqueteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime; // <- IMPORTADO
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class CorrespondenciaController {

    private final PaqueteService paqueteService;
    private final UsuarioRepository usuarioRepository;
    private final RetpostoRepository retpostoRepository;

    // --- FORMATEADORES ELIMINADOS ---
    // private final DateTimeFormatter dtfFecha = ...
    // private final DateTimeFormatter dtfHora = ...

    @Autowired
    public CorrespondenciaController(PaqueteService paqueteService, UsuarioRepository usuarioRepository, RetpostoRepository retpostoRepository) {
        this.paqueteService = paqueteService;
        this.usuarioRepository = usuarioRepository;
        this.retpostoRepository = retpostoRepository;
    }

    // --- Métodos Auxiliares (Sin cambios) ---
    private String getDniUsuario(Authentication authentication) {
        return authentication.getName();
    }
    private Integer getCentroUsuario(Authentication authentication) {
        String dni = getDniUsuario(authentication);
        return usuarioRepository.findByUsuDni(dni)
                .map(Usuario::getUsuCentro)
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado o sin centro asignado"));
    }
    private void cargarPersonal(Model model, Integer centroUsuario) {
        List<Retposto> personas = retpostoRepository.findByRptCentroAndRptEmailIsNotEmptyOrderByRptNombreAsc(centroUsuario);
        model.addAttribute("listaPersonas", personas);
    }

    // (Formulario Nuevo - Sin cambios)
    @GetMapping("/correspondencia/nueva")
    public String mostrarFormularioNuevo(Model model, Authentication authentication) {
        Integer centroUsuario = getCentroUsuario(authentication);
        Paquete paquete = new Paquete();
        paquete.setPktCentro(centroUsuario);
        model.addAttribute("paquete", paquete);
        cargarPersonal(model, centroUsuario);
        model.addAttribute("pageTitle", "Registrar Nueva Correspondencia");
        model.addAttribute("view", "vistas-formularios/form-correspondencia");
        return "layouts/layout";
    }

    // (Guardar Paquete - ¡ACTUALIZADO!)
    @PostMapping("/correspondencia/guardar")
    public String guardarPaquete(@ModelAttribute("paquete") Paquete paquete, Authentication authentication) {
        Integer centroUsuario = getCentroUsuario(authentication);
        String dniUsuario = getDniUsuario(authentication);

        if (!paquete.getPktCentro().equals(centroUsuario)) {
            return "redirect:/panel?error=accesoDenegado";
        }

        // --- LÓGICA DE FECHA ACTUALIZADA ---
        paquete.setPktFechaHoraRecepcionDt(LocalDateTime.now());
        paquete.setPktOperario(dniUsuario);
        paquete.setPktComunicado("No"); // Estado inicial "Pendiente"

        paqueteService.save(paquete);
        return "redirect:/panel";
    }

    // (Lista Pendientes - Sin cambios)
    @GetMapping("/correspondencia/entrega")
    public String mostrarListaPendientes(Model model, Authentication authentication,
                                         @RequestParam(name = "page", defaultValue = "0") int page,
                                         @RequestParam(name = "keyword", required = false) String keyword) {
        Integer centroUsuario = getCentroUsuario(authentication);
        Pageable pageable = PageRequest.of(page, 10);
        Page<Paquete> paquetesPage = paqueteService.findPendientesByCentro(centroUsuario, keyword, pageable);
        model.addAttribute("paquetesPage", paquetesPage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("baseURL", "/correspondencia/entrega");
        int totalPaginas = paquetesPage.getTotalPages();
        if (totalPaginas > 0) {
            List<Integer> numerosPagina = IntStream.rangeClosed(1, totalPaginas).map(i -> i - 1).boxed().collect(Collectors.toList());
            model.addAttribute("numerosPagina", numerosPagina);
        }
        model.addAttribute("pageTitle", "Entregar Correspondencia Pendiente");
        model.addAttribute("view", "vistas-listados/list-correspondencia-pendiente");
        return "layouts/layout";
    }

    // (Marcar Entregado - ¡ACTUALIZADO!)
    @PostMapping("/correspondencia/marcar-entregado/{id}")
    public String marcarEntregado(@PathVariable("id") Integer id, Authentication authentication,
                                  @RequestParam(name = "page", defaultValue = "0") int page,
                                  @RequestParam(name = "keyword", required = false) String keyword) {

        Integer centroUsuario = getCentroUsuario(authentication);
        String dniUsuario = getDniUsuario(authentication);
        Paquete paquete = paqueteService.findByIdAndCentro(id, centroUsuario)
                .orElseThrow(() -> new IllegalArgumentException("ID de paquete inválido o acceso denegado"));

        paquete.setPktComunicado("Si");

        // --- LÓGICA DE FECHA ACTUALIZADA ---
        paquete.setPktFechaHoraComunicaDt(LocalDateTime.now());
        paquete.setPktOperarioComunica(dniUsuario);
        paquete.setPktTipoComunicado("En Mano");

        paqueteService.save(paquete);

        String redirectUrl = "/correspondencia/entrega?page=" + page;
        if (keyword != null && !keyword.isEmpty()) {
            redirectUrl += "&keyword=" + keyword;
        }
        return "redirect:" + redirectUrl;
    }

    // (Informe - Sin cambios)
    @GetMapping("/correspondencia/informes")
    public String mostrarInforme(Model model, Authentication authentication,
                                 @RequestParam(name = "page", defaultValue = "0") int page,
                                 @RequestParam(name = "keyword", required = false) String keyword) {
        Integer centroUsuario = getCentroUsuario(authentication);
        Pageable pageable = PageRequest.of(page, 10);
        Page<Paquete> paquetesPage = paqueteService.findAllByCentro(centroUsuario, keyword, pageable);
        model.addAttribute("paquetesPage", paquetesPage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("baseURL", "/correspondencia/informes");
        int totalPaginas = paquetesPage.getTotalPages();
        if (totalPaginas > 0) {
            List<Integer> numerosPagina = IntStream.rangeClosed(1, totalPaginas).map(i -> i - 1).boxed().collect(Collectors.toList());
            model.addAttribute("numerosPagina", numerosPagina);
        }
        model.addAttribute("pageTitle", "Informe Histórico de Correspondencia");
        model.addAttribute("view", "vistas-listados/list-correspondencia-informe");
        return "layouts/layout";
    }
}