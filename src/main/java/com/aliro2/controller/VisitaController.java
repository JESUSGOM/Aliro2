package com.aliro2.controller;

import com.aliro2.model.Alquiler;
import com.aliro2.model.Movadoj;
import com.aliro2.model.Planta;
import com.aliro2.model.Usuario;
import com.aliro2.repository.UsuarioRepository;
import com.aliro2.service.AlquilerService;
import com.aliro2.service.MovadojService;
import com.aliro2.service.PlantaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime; // <- IMPORTADO (ya no se usa LocalDate ni LocalTime)
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class VisitaController {

    private final MovadojService movadojService;
    private final UsuarioRepository usuarioRepository;
    private final PlantaService plantaService;
    private final AlquilerService alquilerService;

    // --- FORMATEADORES ELIMINADOS ---
    // private final DateTimeFormatter dtfFecha = ...
    // private final DateTimeFormatter dtfHora = ...

    @Autowired
    public VisitaController(MovadojService movadojService, UsuarioRepository usuarioRepository, PlantaService plantaService, AlquilerService alquilerService) {
        this.movadojService = movadojService;
        this.usuarioRepository = usuarioRepository;
        this.plantaService = plantaService;
        this.alquilerService = alquilerService;
    }

    // Método auxiliar (sin cambios)
    private Integer getCentroUsuario(Authentication authentication) {
        String dni = authentication.getName();
        return usuarioRepository.findByUsuDni(dni)
                .map(Usuario::getUsuCentro)
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado o sin centro asignado"));
    }

    // --- MÉTODOS DE VISITAS (Entrada, Salida, Guardar, Editar) ---

    // (Sin cambios)
    @GetMapping("/visitas/entrada")
    public String mostrarFormularioEntrada(Model model, Authentication authentication) {
        Integer centroUsuario = getCentroUsuario(authentication);
        Movadoj nuevaVisita = new Movadoj();
        nuevaVisita.setMovCentro(centroUsuario);
        List<Planta> plantas = plantaService.findByCentro(centroUsuario);
        List<Alquiler> alquileres = alquilerService.findByCentro(centroUsuario);
        model.addAttribute("visita", nuevaVisita);
        model.addAttribute("plantas", plantas);
        model.addAttribute("alquileres", alquileres);
        model.addAttribute("pageTitle", "Registrar Entrada de Visita");
        model.addAttribute("view", "vistas-formularios/form-visita");
        return "layouts/layout";
    }

    // (¡ACTUALIZADO!)
    @PostMapping("/visitas/guardar")
    public String guardarVisita(@ModelAttribute("visita") Movadoj visita, Authentication authentication) {
        Integer centroUsuario = getCentroUsuario(authentication);
        if (!visita.getMovCentro().equals(centroUsuario)) {
            return "redirect:/panel?error=accesoDenegado";
        }

        // --- LÓGICA DE FECHA ACTUALIZADA ---
        // Comprueba el ID (MovOrden) para saber si es nuevo
        if (visita.getMovOrden() == null) {
            // Si es una visita nueva, establece la fecha/hora de entrada
            visita.setMovFechaHoraEntradaDt(LocalDateTime.now());
        }
        // Si es una edición (getMovOrden() no es null), la fecha de entrada no se toca.

        movadojService.save(visita);
        return "redirect:/panel";
    }

    // (Sin cambios en la lógica, pero ahora depende del Service refactorizado)
    @GetMapping("/visitas/salida")
    public String mostrarFormularioSalida(Model model,
                                          @RequestParam(name = "page", defaultValue = "0") int page,
                                          @RequestParam(name = "keyword", required = false) String keyword,
                                          Authentication authentication) {
        Integer centroUsuario = getCentroUsuario(authentication);
        Pageable pageable = PageRequest.of(page, 7);
        // El servicio (refactorizado) se encarga de buscar solo las de HOY
        Page<Movadoj> visitasPage = movadojService.findVisitasActivas(keyword, centroUsuario, pageable);
        model.addAttribute("visitasPage", visitasPage);
        model.addAttribute("keyword", keyword);
        int totalPaginas = visitasPage.getTotalPages();
        if (totalPaginas > 0) {
            List<Integer> numerosPagina = IntStream.rangeClosed(1, totalPaginas).map(i -> i - 1).boxed().collect(Collectors.toList());
            model.addAttribute("numerosPagina", numerosPagina);
        }
        model.addAttribute("pageTitle", "Registrar Salida de Visita");
        model.addAttribute("view", "vistas-listados/list-visitas-salida");
        return "layouts/layout";
    }

    // (¡ACTUALIZADO!)
    @PostMapping("/visitas/registrar-salida/{id}")
    public String registrarSalida(@PathVariable("id") Integer id,
                                  @RequestParam(name = "page", defaultValue = "0") int page,
                                  @RequestParam(name = "keyword", required = false) String keyword,
                                  Authentication authentication) {
        Integer centroUsuario = getCentroUsuario(authentication);
        Movadoj visita = movadojService.findByIdAndMovCentro(id, centroUsuario)
                .orElseThrow(() -> new IllegalArgumentException("ID de visita inválido o acceso denegado:" + id));

        // --- LÓGICA DE FECHA ACTUALIZADA ---
        visita.setMovFechaHoraSalidaDt(LocalDateTime.now());

        movadojService.save(visita);

        String redirectUrl = "/visitas/salida?page=" + page;
        if (keyword != null && !keyword.isEmpty()) {
            redirectUrl += "&keyword=" + keyword;
        }
        return "redirect:" + redirectUrl;
    }

    // (Sin cambios)
    @GetMapping("/visitas/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Integer id, Model model, Authentication authentication) {
        Integer centroUsuario = getCentroUsuario(authentication);
        Movadoj visita = movadojService.findByIdAndMovCentro(id, centroUsuario)
                .orElseThrow(() -> new IllegalArgumentException("ID de visita inválido o acceso denegado:" + id));
        model.addAttribute("visita", visita);
        List<Planta> plantas = plantaService.findByCentro(centroUsuario);
        List<Alquiler> alquileres = alquilerService.findByCentro(centroUsuario);
        model.addAttribute("plantas", plantas);
        model.addAttribute("alquileres", alquileres);
        model.addAttribute("pageTitle", "Editar Visita");
        model.addAttribute("view", "vistas-formularios/form-visita");
        return "layouts/layout";
    }

    // --- MÉTODOS DE INFORMES (Sin cambios en el controlador) ---

    @GetMapping("/visitas/informes")
    public String mostrarSubmenuInformes(Model model) {
        model.addAttribute("pageTitle", "Informes de Visitantes");
        model.addAttribute("view", "vistas-informes/submenu-visitas");
        return "layouts/layout";
    }

    @GetMapping("/visitas/informes/general")
    public String mostrarInformeGeneral(Model model, Authentication authentication,
                                        @RequestParam(name = "page", defaultValue = "0") int page,
                                        @RequestParam(name = "keyword", required = false) String keyword) {
        Integer centroUsuario = getCentroUsuario(authentication);
        Pageable pageable = PageRequest.of(page, 7);
        Page<Movadoj> visitasPage = movadojService.findTodasVisitasActivasPorCentro(keyword, centroUsuario, pageable);
        model.addAttribute("visitasPage", visitasPage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("baseURL", "/visitas/informes/general");
        int totalPaginas = visitasPage.getTotalPages();
        if (totalPaginas > 0) {
            List<Integer> numerosPagina = IntStream.rangeClosed(1, totalPaginas).map(i -> i - 1).boxed().collect(Collectors.toList());
            model.addAttribute("numerosPagina", numerosPagina);
        }
        model.addAttribute("pageTitle", "Informe General de Visitantes (Activos)");
        model.addAttribute("view", "vistas-listados/list-visitas-informe");
        return "layouts/layout";
    }

    @GetMapping("/visitas/informes/movimientos")
    public String mostrarInformeConMovimientos(Model model, Authentication authentication,
                                               @RequestParam(name = "page", defaultValue = "0") int page,
                                               @RequestParam(name = "keyword", required = false) String keyword) {
        Integer centroUsuario = getCentroUsuario(authentication);
        Pageable pageable = PageRequest.of(page, 7);
        Page<Movadoj> visitasPage = movadojService.findAllByCentro(keyword, centroUsuario, pageable);
        model.addAttribute("visitasPage", visitasPage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("baseURL", "/visitas/informes/movimientos");
        int totalPaginas = visitasPage.getTotalPages();
        if (totalPaginas > 0) {
            List<Integer> numerosPagina = IntStream.rangeClosed(1, totalPaginas).map(i -> i - 1).boxed().collect(Collectors.toList());
            model.addAttribute("numerosPagina", numerosPagina);
        }
        model.addAttribute("pageTitle", "Informe Histórico de Movimientos");
        model.addAttribute("view", "vistas-listados/list-visitas-informe");
        return "layouts/layout";
    }
}