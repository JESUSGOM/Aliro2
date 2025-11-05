package com.aliro2.controller;

import com.aliro2.model.Alquiler; // <- 1. IMPORTAR Alquiler
import com.aliro2.model.Movadoj;
import com.aliro2.model.Planta; // <- 2. IMPORTAR Planta
import com.aliro2.model.Usuario;
import com.aliro2.repository.UsuarioRepository;
import com.aliro2.service.AlquilerService; // <- 3. IMPORTAR AlquilerService
import com.aliro2.service.MovadojService;
import com.aliro2.service.PlantaService; // <- 4. IMPORTAR PlantaService
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
    private final PlantaService plantaService; // <- 5. AÑADIR PlantaService
    private final AlquilerService alquilerService; // <- 6. AÑADIR AlquilerService

    private final DateTimeFormatter dtfFecha = DateTimeFormatter.ofPattern("yyyyMMdd");
    private final DateTimeFormatter dtfHora = DateTimeFormatter.ofPattern("HHmm");

    @Autowired
    public VisitaController(MovadojService movadojService, UsuarioRepository usuarioRepository, PlantaService plantaService, AlquilerService alquilerService) { // <- 7. AÑADIR al constructor
        this.movadojService = movadojService;
        this.usuarioRepository = usuarioRepository;
        this.plantaService = plantaService; // <- 8. AÑADIR
        this.alquilerService = alquilerService; // <- 9. AÑADIR
    }

    // Método auxiliar para obtener el centro del usuario logueado
    private Integer getCentroUsuario(Authentication authentication) {
        String dni = authentication.getName();
        return usuarioRepository.findByUsuDni(dni)
                .map(Usuario::getUsuCentro)
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado o sin centro asignado"));
    }

    /**
     * MUESTRA EL SUBMENÚ DE INFORMES DE VISITAS
     * Se activa con el botón: "Informes Visitantes"
     * URL: /visitas/informes
     * * **** ESTE ES EL MÉTODO MODIFICADO ****
     */
    @GetMapping("/visitas/informes")
    public String mostrarSubmenuInformes(Model model) {

        // Ya no necesitamos cargar ninguna lista de visitas aquí
        // Solo le decimos a Thymeleaf qué fragmento (el nuevo submenú) debe mostrar

        model.addAttribute("pageTitle", "Informes de Visitantes");
        // Apuntamos a un NUEVO fragmento HTML
        model.addAttribute("view", "vistas-informes/submenu-visitas");
        return "layouts/layout"; // Devolvemos el layout principal
    }

    /**
     * MUESTRA EL FORMULARIO DE NUEVA VISITA
     * (Botón "Entrada Visitantes")
     * **** MÉTODO ACTUALIZADO ****
     */
    @GetMapping("/visitas/entrada")
    public String mostrarFormularioEntrada(Model model, Authentication authentication) {
        Integer centroUsuario = getCentroUsuario(authentication);

        Movadoj nuevaVisita = new Movadoj();
        nuevaVisita.setMovCentro(centroUsuario);

        // 10. OBTENER Y AÑADIR LAS LISTAS DE PLANTAS Y ALQUILERES
        List<Planta> plantas = plantaService.findByCentro(centroUsuario);
        List<Alquiler> alquileres = alquilerService.findByCentro(centroUsuario);

        model.addAttribute("visita", nuevaVisita);
        model.addAttribute("plantas", plantas); // <- PASAR PLANTAS A LA VISTA
        model.addAttribute("alquileres", alquileres); // <- PASAR ALQUILERES A LA VISTA
        model.addAttribute("pageTitle", "Registrar Entrada de Visita");
        model.addAttribute("view", "vistas-formularios/form-visita");
        return "layouts/layout";
    }

    @PostMapping("/visitas/guardar")
    public String guardarVisita(@ModelAttribute("visita") Movadoj visita, Authentication authentication) {
        Integer centroUsuario = getCentroUsuario(authentication);
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
                                          Authentication authentication) {

        Integer centroUsuario = getCentroUsuario(authentication);
        Pageable pageable = PageRequest.of(page, 7);
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
                                  Authentication authentication) {

        Integer centroUsuario = getCentroUsuario(authentication);
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
     * MUESTRA EL INFORME "GENERAL DE VISITANTES" (SOLO ACTIVOS)
     * Se activa desde el nuevo submenú
     * URL: /visitas/informes/general
     */
    @GetMapping("/visitas/informes/general")
    public String mostrarInformeGeneral(Model model, Authentication authentication,
                                        @RequestParam(name = "page", defaultValue = "0") int page,
                                        @RequestParam(name = "keyword", required = false) String keyword) {

        Integer centroUsuario = getCentroUsuario(authentication);
        Pageable pageable = PageRequest.of(page, 10);

        // Llama al método que busca TODAS las visitas ACTIVAS (sin filtro de fecha)
        Page<Movadoj> visitasPage = movadojService.findTodasVisitasActivasPorCentro(keyword, centroUsuario, pageable);

        model.addAttribute("visitasPage", visitasPage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("baseURL", "/visitas/informes/general"); // Para la paginación

        int totalPaginas = visitasPage.getTotalPages();
        if (totalPaginas > 0) {
            List<Integer> numerosPagina = IntStream.rangeClosed(1, totalPaginas).map(i -> i - 1).boxed().collect(Collectors.toList());
            model.addAttribute("numerosPagina", numerosPagina);
        }

        model.addAttribute("pageTitle", "Informe General de Visitantes (Activos)");
        // Reutilizamos la vista de lista que ya teníamos (y que vamos a corregir)
        model.addAttribute("view", "vistas-listados/list-visitas-informe");
        return "layouts/layout";
    }

    /**
     * MUESTRA EL INFORME "GENERAL CON MOVIMIENTOS" (TODO EL HISTÓRICO)
     * Se activa desde el nuevo submenú
     * URL: /visitas/informes/movimientos
     */
    @GetMapping("/visitas/informes/movimientos")
    public String mostrarInformeConMovimientos(Model model, Authentication authentication,
                                               @RequestParam(name = "page", defaultValue = "0") int page,
                                               @RequestParam(name = "keyword", required = false) String keyword) {

        Integer centroUsuario = getCentroUsuario(authentication);
        Pageable pageable = PageRequest.of(page, 10);

        // Llama al NUEVO método de servicio que busca TODO (incluyendo las visitas cerradas)
        Page<Movadoj> visitasPage = movadojService.findAllByCentro(keyword, centroUsuario, pageable);

        model.addAttribute("visitasPage", visitasPage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("baseURL", "/visitas/informes/movimientos"); // Para la paginación

        int totalPaginas = visitasPage.getTotalPages();
        if (totalPaginas > 0) {
            List<Integer> numerosPagina = IntStream.rangeClosed(1, totalPaginas).map(i -> i - 1).boxed().collect(Collectors.toList());
            model.addAttribute("numerosPagina", numerosPagina);
        }

        model.addAttribute("pageTitle", "Informe Histórico de Movimientos");
        // Reutilizamos la misma vista de lista
        model.addAttribute("view", "vistas-listados/list-visitas-informe");
        return "layouts/layout";
    }

    /**
     * MUESTRA EL SUBMENÚ DE INFORMES DE VISITAS
     * (Botón "Informes Visitantes")
     * URL: /visitas/informes
     * * **** ESTE ES EL MÉTODO MODIFICADO ****
     */
    @GetMapping("/visitas/informes")
    public String mostrarSubmenuInformes(Model model) {

        // Ya no cargamos ninguna lista, solo mostramos el submenú

        model.addAttribute("pageTitle", "Informes de Visitantes");
        // Apuntamos a un NUEVO fragmento HTML
        model.addAttribute("view", "vistas-informes/submenu-visitas");
        return "layouts/layout"; // Devolvemos el layout principal
    }

    /**
     * MUESTRA EL INFORME DE TODAS LAS VISITAS (ACTIVAS, DE CUALQUIER FECHA, POR CENTRO)
     * URL: /visitas/informes
     * * **** ASEGÚRATE DE QUE TU MÉTODO ES ASÍ ****
     */
    @GetMapping("/visitas/informes")
    public String mostrarInformeVisitas(Model model, Authentication authentication,
                                        @RequestParam(name = "page", defaultValue = "0") int page,
                                        @RequestParam(name = "keyword", required = false) String keyword) {

        Integer centroUsuario = getCentroUsuario(authentication);
        Pageable pageable = PageRequest.of(page, 10);

        // CORRECCIÓN: Asegúrate de que llama a 'findTodasVisitasActivasPorCentro'
        Page<Movadoj> visitasPage = movadojService.findTodasVisitasActivasPorCentro(keyword, centroUsuario, pageable);

        model.addAttribute("visitasPage", visitasPage); // El objeto se llama "visitasPage"
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

    /**
     * MUESTRA EL FORMULARIO PARA EDITAR (FUNCIONALIDAD EXTRA)
     * **** MÉTODO ACTUALIZADO ****
     */
    @GetMapping("/visitas/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Integer id, Model model, Authentication authentication) {
        Integer centroUsuario = getCentroUsuario(authentication);
        Movadoj visita = movadojService.findByIdAndMovCentro(id, centroUsuario)
                .orElseThrow(() -> new IllegalArgumentException("ID de visita inválido o acceso denegado:" + id));

        model.addAttribute("visita", visita);

        // 11. AÑADIR TAMBIÉN LAS PLANTAS Y ALQUILERES A LA VISTA DE EDICIÓN
        List<Planta> plantas = plantaService.findByCentro(centroUsuario);
        List<Alquiler> alquileres = alquilerService.findByCentro(centroUsuario);
        model.addAttribute("plantas", plantas);
        model.addAttribute("alquileres", alquileres);

        model.addAttribute("pageTitle", "Editar Visita");
        model.addAttribute("view", "vistas-formularios/form-visita"); // Reutilizamos el formulario
        return "layouts/layout";
    }
}