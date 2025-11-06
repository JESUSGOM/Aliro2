package com.aliro2.controller;

import com.aliro2.model.Telefono;
import com.aliro2.model.Usuario;
import com.aliro2.repository.UsuarioRepository;
import com.aliro2.service.TelefonoService;
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
public class LlamadaController {

    private final TelefonoService telefonoService;
    private final UsuarioRepository usuarioRepository;

    // Formateadores para el formato de tu BD (AAAAMMDD y HHMM)
    private final DateTimeFormatter dtfFecha = DateTimeFormatter.ofPattern("yyyyMMdd");
    private final DateTimeFormatter dtfHora = DateTimeFormatter.ofPattern("HHmm");

    @Autowired
    public LlamadaController(TelefonoService telefonoService, UsuarioRepository usuarioRepository) {
        this.telefonoService = telefonoService;
        this.usuarioRepository = usuarioRepository;
    }

    // Método auxiliar para obtener el centro del usuario logueado
    private Integer getCentroUsuario(Authentication authentication) {
        String dni = authentication.getName();
        return usuarioRepository.findByUsuDni(dni)
                .map(Usuario::getUsuCentro)
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado o sin centro asignado"));
    }

    /**
     * MUESTRA EL FORMULARIO PARA REGISTRAR UNA NUEVA LLAMADA
     * Se activa con: "Llamadas Telefónicas"
     */
    @GetMapping("/llamadas/nueva")
    public String mostrarFormularioNueva(Model model, Authentication authentication) {
        Integer centroUsuario = getCentroUsuario(authentication);

        Telefono telefono = new Telefono();
        telefono.setTelCentro(centroUsuario); // Asigna el centro por defecto

        model.addAttribute("telefono", telefono);
        model.addAttribute("pageTitle", "Registrar Llamada Telefónica");
        model.addAttribute("view", "vistas-formularios/form-llamada");
        return "layouts/layout";
    }
    /**
     * GUARDA LA NUEVA LLAMADA
     * Se activa desde la acción POST del formulario
     */
    @PostMapping("/llamadas/guardar")
    public String guardarLlamada(@ModelAttribute("telefono") Telefono telefono, Authentication authentication) {

        Integer centroUsuario = getCentroUsuario(authentication);
        // Seguridad: Asegurarse de que el centro coincide
        if (!telefono.getTelCentro().equals(centroUsuario)) {
            return "redirect:/panel?error=accesoDenegado";
        }

        // Seteamos la fecha y hora de registro
        telefono.setTelFecha(LocalDate.now().format(dtfFecha));
        telefono.setTelHora(LocalTime.now().format(dtfHora));
        telefono.setTelComunicado(0); // 0 = No comunicado

        telefonoService.save(telefono);
        return "redirect:/panel"; // Vuelve al panel principal
    }

    /**
     * MUESTRA LA LISTA DE LLAMADAS PENDIENTES
     * Se activa con: "Comunicar llamadas"
     */
    @GetMapping("/llamadas/comunicar")
    public String mostrarListaPendientes(Model model, Authentication authentication,
                                         @RequestParam(name = "page", defaultValue = "0") int page,
                                         @RequestParam(name = "keyword", required = false) String keyword) {

        Integer centroUsuario = getCentroUsuario(authentication);
        Pageable pageable = PageRequest.of(page, 10);

        Page<Telefono> llamadasPage = telefonoService.findPendientesByCentro(centroUsuario, keyword, pageable);

        model.addAttribute("llamadasPage", llamadasPage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("baseURL", "/llamadas/comunicar"); // Para la paginación

        int totalPaginas = llamadasPage.getTotalPages();
        if (totalPaginas > 0) {
            List<Integer> numerosPagina = IntStream.rangeClosed(1, totalPaginas).map(i -> i - 1).boxed().collect(Collectors.toList());
            model.addAttribute("numerosPagina", numerosPagina);
        }

        model.addAttribute("pageTitle", "Comunicar Llamadas Pendientes");
        model.addAttribute("view", "vistas-listados/list-llamadas-pendientes");
        return "layouts/layout";
    }

    /**
     * MARCA UNA LLAMADA COMO COMUNICADA (ACTUALIZA)
     * Se activa desde el botón "Comunicar" en la lista
     */
    @PostMapping("/llamadas/marcar-comunicada/{id}")
    public String marcarComunicada(@PathVariable("id") Integer id, Authentication authentication,
                                   @RequestParam(name = "page", defaultValue = "0") int page,
                                   @RequestParam(name = "keyword", required = false) String keyword) {

        Integer centroUsuario = getCentroUsuario(authentication);

        // Seguridad: Busca la llamada y valida que pertenezca al centro del usuario
        Telefono telefono = telefonoService.findByIdAndCentro(id, centroUsuario)
                .orElseThrow(() -> new IllegalArgumentException("ID de llamada inválido o acceso denegado"));

        // Actualizamos los campos
        telefono.setTelComunicado(1); // 1 = Comunicado
        telefono.setTelFechaEntrega(LocalDate.now().format(dtfFecha));
        telefono.setTelHoraEntrega(LocalTime.now().format(dtfHora));

        telefonoService.save(telefono);

        String redirectUrl = "/llamadas/comunicar?page=" + page;
        if (keyword != null && !keyword.isEmpty()) {
            redirectUrl += "&keyword=" + keyword;
        }
        return "redirect:" + redirectUrl;
    }

    /**
     * MUESTRA EL INFORME DE TODAS LAS LLAMADAS
     * Se activa con: "Informes Llamadas"
     */
    @GetMapping("/llamadas/informes")
    public String mostrarInforme(Model model, Authentication authentication,
                                 @RequestParam(name = "page", defaultValue = "0") int page,
                                 @RequestParam(name = "keyword", required = false) String keyword) {

        Integer centroUsuario = getCentroUsuario(authentication);
        Pageable pageable = PageRequest.of(page, 10);

        Page<Telefono> llamadasPage = telefonoService.findAllByCentro(centroUsuario, keyword, pageable);

        model.addAttribute("llamadasPage", llamadasPage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("baseURL", "/llamadas/informes"); // Para la paginación

        int totalPaginas = llamadasPage.getTotalPages();
        if (totalPaginas > 0) {
            List<Integer> numerosPagina = IntStream.rangeClosed(1, totalPaginas).map(i -> i - 1).boxed().collect(Collectors.toList());
            model.addAttribute("numerosPagina", numerosPagina);
        }

        model.addAttribute("pageTitle", "Informe de Llamadas Telefónicas");
        model.addAttribute("view", "vistas-listados/list-llamadas-informe");
        return "layouts/layout";
    }
}