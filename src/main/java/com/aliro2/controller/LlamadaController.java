package com.aliro2.controller;

import com.aliro2.model.Telefono;
import com.aliro2.model.Usuario;
import com.aliro2.model.Retposto;
import com.aliro2.repository.RetpostoRepository;
import com.aliro2.repository.UsuarioRepository;
import com.aliro2.service.EnvioEmailService; // Asumiendo que este es el nombre correcto
import com.aliro2.service.TelefonoService;
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
public class LlamadaController {

    private final TelefonoService telefonoService;
    private final UsuarioRepository usuarioRepository;
    private final RetpostoRepository retpostoRepository;
    private final EnvioEmailService envioEmailService; // Asumimos este nombre

    // --- FORMATEADORES ELIMINADOS ---
    // private final DateTimeFormatter dtfFecha = ...
    // private final DateTimeFormatter dtfHora = ...

    @Autowired
    public LlamadaController(TelefonoService telefonoService,
                             UsuarioRepository usuarioRepository,
                             RetpostoRepository retpostoRepository,
                             EnvioEmailService envioEmailService) {
        this.telefonoService = telefonoService;
        this.usuarioRepository = usuarioRepository;
        this.retpostoRepository = retpostoRepository;
        this.envioEmailService = envioEmailService;
    }

    // Método auxiliar (sin cambios)
    private Usuario getUsuarioCompleto(Authentication authentication) {
        String dni = authentication.getName();
        return usuarioRepository.findByUsuDni(dni)
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado"));
    }

    // Método auxiliar (sin cambios)
    private Integer getCentroUsuario(Authentication authentication) {
        return getUsuarioCompleto(authentication).getUsuCentro();
    }


    /**
     * MUESTRA EL FORMULARIO PARA REGISTRAR UNA NUEVA LLAMADA
     * (Sin cambios, pero el 'emilio' referenciado en el HTML [cite: 98] no existe en el modelo Telefono.java)
     */
    @GetMapping("/llamadas/nueva")
    public String mostrarFormularioNueva(Model model, Authentication authentication) {
        Usuario usuario = getUsuarioCompleto(authentication);
        Integer centroUsuario = usuario.getUsuCentro();
        Telefono telefono = new Telefono();
        telefono.setTelCentro(centroUsuario);

        model.addAttribute("nuevaLlamada", telefono);
        model.addAttribute("identifico", usuario.getUsuDni());
        model.addAttribute("apellidoUno", usuario.getUsuApellidoUno());
        model.addAttribute("apellidoDos", usuario.getUsuApellidoDos());
        model.addAttribute("nombre", usuario.getUsuNombre());

        // Asumiendo que tienes un getter para la denominación del centro en Usuario
        // model.addAttribute("centroDen", usuario.getUsuCentroDenominacion());
        model.addAttribute("numero", usuario.getUsuCentro());

        String estaidentificacion = "**" + usuario.getUsuDni().substring(2, 4) + "*" + usuario.getUsuDni().substring(5, 6) + "**" + usuario.getUsuDni().substring(8, 9);
        model.addAttribute("estaidentificacion", estaidentificacion);

        List<Retposto> personas = retpostoRepository.findByRptCentroAndRptEmailIsNotEmptyOrderByRptNombreAsc(centroUsuario);
        model.addAttribute("listaPersonas", personas);

        model.addAttribute("pageTitle", "Registrar Llamada Telefónica");
        model.addAttribute("view", "vistas-formularios/form-llamada");
        return "layouts/layout";
    }

    /**
     * GUARDA LA NUEVA LLAMADA (¡ACTUALIZADO!)
     */
    @PostMapping("/llamadas/guardar")
    public String guardarLlamada(
            @ModelAttribute("nuevaLlamada") Telefono telefono,
            Authentication authentication,
            @RequestParam("nom") String nombreUsuario,
            @RequestParam("cen") String centroDenUsuario,
            // El campo 'emilio' [cite: 98] viene del <select> pero no está en el modelo Telefono
            @RequestParam("emilio") String emilio) { // Capturamos 'emilio' por separado

        Integer centroUsuario = getCentroUsuario(authentication);
        if (!telefono.getTelCentro().equals(centroUsuario)) {
            return "redirect:/panel?error=accesoDenegado";
        }

        // --- LÓGICA DE FECHA ACTUALIZADA ---
        telefono.setTelFechaHoraRegistroDt(LocalDateTime.now());
        telefono.setTelComunicado(0); // Pendiente

        // 1. Guardar la llamada en la BD
        telefonoService.save(telefono);

        // 2. Enviar el email (Lógica copiada de tu controlador original [cite: 97-101])
        try {
            String emailDestino = "";
            if (emilio != null && emilio.contains("|")) {
                emailDestino = emilio.split("\\|")[1].trim();
            }

            if (!emailDestino.isEmpty() && !emailDestino.equals("sincorreo")) {
                envioEmailService.enviarEmailLlamada(
                        emailDestino,
                        nombreUsuario,
                        centroDenUsuario,
                        telefono.getTelEmisor(),
                        telefono.getTelDestinatario(),
                        telefono.getTelMensaje(),
                        authentication.getName() // Pasamos el DNI del emisor para el log
                );
            }
        } catch (Exception e) {
            System.err.println("Error al enviar email: " + e.getMessage());
        }

        return "redirect:/panel";
    }

    /**
     * MUESTRA LISTA DE PENDIENTES (Sin cambios)
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
        model.addAttribute("baseURL", "/llamadas/comunicar");
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
     * MARCAR COMO COMUNICADA (¡ACTUALIZADO!)
     */
    @PostMapping("/llamadas/marcar-comunicada/{id}")
    public String marcarComunicada(@PathVariable("id") Integer id, Authentication authentication,
                                   @RequestParam(name = "page", defaultValue = "0") int page,
                                   @RequestParam(name = "keyword", required = false) String keyword) {

        Integer centroUsuario = getCentroUsuario(authentication);
        Telefono telefono = telefonoService.findByIdAndCentro(id, centroUsuario)
                .orElseThrow(() -> new IllegalArgumentException("ID de llamada inválido o acceso denegado"));

        telefono.setTelComunicado(1);

        // --- LÓGICA DE FECHA ACTUALIZADA ---
        telefono.setTelFechaHoraEntregaDt(LocalDateTime.now());

        telefonoService.save(telefono);

        String redirectUrl = "/llamadas/comunicar?page=" + page;
        if (keyword != null && !keyword.isEmpty()) {
            redirectUrl += "&keyword=" + keyword;
        }
        return "redirect:" + redirectUrl;
    }

    /**
     * MOSTRAR INFORME (Sin cambios)
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
        model.addAttribute("baseURL", "/llamadas/informes");

        int totalPaginas = llamadasPage.getTotalPages();
        if (totalPaginas > 0) {
            List<Integer> numerosPagina = IntStream.rangeClosed(1, totalPaginas).map(i -> i - 1).boxed().collect(Collectors.toList());
            model.addAttribute("numerosPagina", numerosPagina);
        }
        model.addAttribute("pageTitle", "Informe de Llamadas");
        model.addAttribute("view", "vistas-listados/list-llamadas-informe");
        return "layouts/layout";
    }
}