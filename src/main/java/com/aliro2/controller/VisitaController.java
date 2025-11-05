package com.aliro2.controller;

import com.aliro2.model.Movadoj;
import com.aliro2.model.Usuario;
import com.aliro2.repository.UsuarioRepository;
import com.aliro2.service.MovadojService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class VisitaController {

    private final MovadojService movadojService;
    private final UsuarioRepository usuarioRepository;

    private final DateTimeFormatter dtfFecha = DateTimeFormatter.ofPattern("yyyyMMdd");
    private final DateTimeFormatter dtfHora = DateTimeFormatter.ofPattern("HHmm");

    @Autowired
    public VisitaController(MovadojService movadojService, UsuarioRepository usuarioRepository) {
        this.movadojService = movadojService;
        this.usuarioRepository = usuarioRepository;
    }

    // --- MÉTODO AUXILIAR PARA OBTENER EL CENTRO DEL USUARIO ---
    private Integer getCentroUsuario(Authentication authentication) {
        String dni = authentication.getName();
        return usuarioRepository.findByUsuDni(dni)
                .map(Usuario::getUsuCentro) // Obtiene el UsuCentro del objeto Usuario
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado o sin centro asignado"));
    }

    @GetMapping("/visitas/entrada")
    public String mostrarFormularioEntrada(Model model, Authentication authentication) {
        Integer centroUsuario = getCentroUsuario(authentication);

        Movadoj nuevaVisita = new Movadoj();
        nuevaVisita.setMovCentro(centroUsuario); // Asigna el centro del usuario

        model.addAttribute("visita", nuevaVisita);
        model.addAttribute("pageTitle", "Registrar Entrada de Visita");
        model.addAttribute("view", "vistas-formularios/form-visita");
        return "layouts/layout";
    }

    @PostMapping("/visitas/guardar")
    public String guardarVisita(@ModelAttribute("visita") Movadoj visita, Authentication authentication) {
        Integer centroUsuario = getCentroUsuario(authentication);

        // --- VALIDACIÓN DE SEGURIDAD ---
        if (!visita.getMovCentro().equals(centroUsuario)) {
            return "redirect:/panel?error=accesoDenegado";
        }

        if (visita.getMovOrden() == null) {
            visita.setMovFechaEntrada(LocalDate.now().format(dtfFecha));
            visita.setMovHoraEntrada(LocalTime.now().format(dtfHora));
        }
        movadojService.save(visita);
        return "redirect:/panel";
    }

    @GetMapping("/visitas/salida")
    public String mostrarFormularioSalida(Model model,
                                          @RequestParam(name = "page", defaultValue = "0") int page,
                                          @RequestParam(name = "keyword", required = false) String keyword,
                                          Authentication authentication) { // <-- Se añade Authentication

        Integer centroUsuario = getCentroUsuario(authentication); // 1. Obtenemos el centro

        int tamanoPagina = 10;
        Pageable pageable = PageRequest.of(page, tamanoPagina);

        // 2. Pasamos el keyword Y el centroUsuario al servicio
        Page<Movadoj> visitasPage = movadojService.findVisitasActivas(keyword, centroUsuario, pageable);

        model.addAttribute("visitasPage", visitasPage);
        model.addAttribute("keyword", keyword);

        int totalPaginas = visitasPage.getTotalPages();
        if (totalPaginas > 0) {
            List<Integer> numerosPagina = IntStream.rangeClosed(1, totalPaginas)
                    .map(i -> i - 1)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("numerosPagina", numerosPagina);
        }

        model.addAttribute("pageTitle", "Registrar Salida de Visita");
        model.addAttribute("view", "vistas-listados/list-visitas-salida");
        return "layouts/layout";
    }

    @PostMapping("/visitas/registrar-salida/{id}")
    public String registrarSalida(@PathVariable("id") Integer id,
                                  @RequestParam(name = "page", defaultValue = "0") int page,
                                  @RequestParam(name = "keyword", required = false) String keyword,
                                  Authentication authentication) { // <-- Se añade Authentication

        Integer centroUsuario = getCentroUsuario(authentication); // 1. Obtenemos el centro

        // 2. Verificamos que la visita sea de ese centro ANTES de modificarla
        Movadoj visita = movadojService.findByIdAndMovCentro(id, centroUsuario)
                .orElseThrow(() -> new IllegalArgumentException("ID de visita inválido o acceso denegado:" + id));

        visita.setMovFechaSalida(LocalDate.now().format(dtfFecha));
        visita.setMovHoraSalida(LocalTime.now().format(dtfHora));
        movadojService.save(visita);

        String redirectUrl = "/visitas/salida?page=" + page;
        if (keyword != null && !keyword.isEmpty()) {
            redirectUrl += "&keyword=" + keyword;
        }
        return "redirect:" + redirectUrl;
    }

    /**
     * MUESTRA EL INFORME DE TODAS LAS VISITAS (ACTIVAS, DE CUALQUIER FECHA, POR CENTRO)
     * URL: /visitas/informes
     */
    @GetMapping("/visitas/informes")
    public String mostrarInformeVisitas(Model model, Authentication authentication,
                                        @RequestParam(name = "page", defaultValue = "0") int page,
                                        @RequestParam(name = "keyword", required = false) String keyword) {

        Integer centroUsuario = getCentroUsuario(authentication);
        Pageable pageable = PageRequest.of(page, 10);

        // Llamamos al método que busca TODAS las visitas activas (sin filtro de fecha)
        Page<Movadoj> visitasPage = movadojService.findTodasVisitasActivasPorCentro(keyword, centroUsuario, pageable);

        model.addAttribute("visitasPage", visitasPage);
        model.addAttribute("keyword", keyword);

        int totalPaginas = visitasPage.getTotalPages();
        if (totalPaginas > 0) {
            List<Integer> numerosPagina = IntStream.rangeClosed(1, totalPaginas).map(i -> i - 1).boxed().collect(Collectors.toList());
            model.addAttribute("numerosPagina", numerosPagina);
        }

        model.addAttribute("pageTitle", "Informe de Visitantes (Activos)");
        model.addAttribute("view", "vistas-listados/list-visitas-informe");
        return "layouts/layout";
    }

    // MUESTRA EL FORMULARIO PARA EDITAR (FUNCIONALIDAD EXTRA)
    @GetMapping("/visitas/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Integer id, Model model, Authentication authentication) {
        Integer centroUsuario = getCentroUsuario(authentication); // 1. Obtenemos el centro

        // 2. Verificamos que la visita a editar sea del centro del usuario
        Movadoj visita = movadojService.findByIdAndMovCentro(id, centroUsuario)
                .orElseThrow(() -> new IllegalArgumentException("ID de visita inválido o acceso denegado:" + id));

        model.addAttribute("visita", visita);
        model.addAttribute("pageTitle", "Editar Visita");
        model.addAttribute("view", "vistas-formularios/form-visita");
        return "layouts/layout";
    }
}