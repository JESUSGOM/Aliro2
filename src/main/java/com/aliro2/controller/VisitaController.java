package com.aliro2.controller;

import com.aliro2.model.Movadoj;
import com.aliro2.model.Usuario;
import com.aliro2.repository.UsuarioRepository; // Importa UsuarioRepository
import com.aliro2.service.MovadojService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication; // Importa Authentication
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Controlador para la lógica de "Gestión de Visitantes" (Entidad Movadoj)
 */
@Controller
public class VisitaController {

    private final MovadojService movadojService;
    private final UsuarioRepository usuarioRepository;

    // Formateadores de fecha/hora (para los campos VARCHAR de la BD)
    private final DateTimeFormatter dtfFecha = DateTimeFormatter.ofPattern("dd/MM/yy");
    private final DateTimeFormatter dtfHora = DateTimeFormatter.ofPattern("HH:mm");

    // Inyección de dependencias por constructor (práctica recomendada)
    @Autowired
    public VisitaController(MovadojService movadojService, UsuarioRepository usuarioRepository) {
        this.movadojService = movadojService;
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * MUESTRA EL FORMULARIO DE NUEVA VISITA
     * Se activa con el botón: "Entrada Visitantes"
     * URL: /visitas/entrada
     */
    @GetMapping("/visitas/entrada")
    public String mostrarFormularioEntrada(Model model, Authentication authentication) {
        // Obtenemos el usuario logueado para saber su centro
        String dni = authentication.getName();
        Usuario usuario = usuarioRepository.findByUsuDni(dni)
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado"));

        // Preparamos un objeto Movadoj vacío y asignamos el centro del usuario
        Movadoj nuevaVisita = new Movadoj();
        nuevaVisita.setMovCentro(usuario.getUsuCentro());

        model.addAttribute("visita", nuevaVisita);
        model.addAttribute("pageTitle", "Registrar Entrada de Visita");
        model.addAttribute("view", "vistas-formularios/form-visita"); // Carga el fragmento del formulario
        return "layouts/layout"; // Carga la plantilla base
    }

    /**
     * GUARDA LA NUEVA VISITA (O ACTUALIZA UNA EXISTENTE)
     * Se activa desde el formulario "form-visita.html"
     * URL: /visitas/guardar
     */
    @PostMapping("/visitas/guardar")
    public String guardarVisita(@ModelAttribute("visita") Movadoj visita) {

        // Si es una visita nueva (no tiene ID), seteamos la fecha y hora de entrada
        if (visita.getMovOrden() == null) {
            visita.setMovFechaEntrada(LocalDate.now().format(dtfFecha));
            visita.setMovHoraEntrada(LocalTime.now().format(dtfHora));
        }

        movadojService.save(visita);
        return "redirect:/panel"; // Vuelve al panel principal
    }

    /**
     * MUESTRA LISTA DE VISITAS ACTIVAS (PARA DAR SALIDA)
     * Se activa con el botón: "Salida Visitantes"
     * URL: /visitas/salida
     */
    @GetMapping("/visitas/salida")
    public String mostrarFormularioSalida(Model model) {
        // Usamos el método personalizado para obtener solo visitas activas
        List<Movadoj> visitasActivas = movadojService.findVisitasActivas();

        model.addAttribute("visitas", visitasActivas);
        model.addAttribute("pageTitle", "Registrar Salida de Visita");
        model.addAttribute("view", "vistas-listados/list-visitas-salida");
        return "layouts/layout";
    }

    /**
     * REGISTRA LA SALIDA (ACTUALIZA)
     * Se activa desde el botón en la lista "list-visitas-salida.html"
     * URL: /visitas/registrar-salida/{id}
     */
    @PostMapping("/visitas/registrar-salida/{id}")
    public String registrarSalida(@PathVariable("id") Integer id) {
        Movadoj visita = movadojService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de visita inválido:" + id));

        // Seteamos la fecha y hora de salida
        visita.setMovFechaSalida(LocalDate.now().format(dtfFecha));
        visita.setMovHoraSalida(LocalTime.now().format(dtfHora));

        movadojService.save(visita);
        return "redirect:/visitas/salida"; // Recarga la lista de salidas
    }

    /**
     * MUESTRA EL INFORME DE TODAS LAS VISITAS
     * Se activa con el botón: "Informes Visitantes"
     * URL: /visitas/informes
     */
    @GetMapping("/visitas/informes")
    public String mostrarInformeVisitas(Model model) {
        model.addAttribute("visitas", movadojService.findAll()); // Muestra todas
        model.addAttribute("pageTitle", "Informe de Visitantes");
        model.addAttribute("view", "vistas-listados/list-visitas-informe");
        return "layouts/layout";
    }

    /**
     * MUESTRA EL FORMULARIO PARA EDITAR (FUNCIONALIDAD EXTRA)
     * Podrías enlazar esto desde la tabla de informes
     */
    @GetMapping("/visitas/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Integer id, Model model) {
        Movadoj visita = movadojService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de visita inválido:" + id));

        model.addAttribute("visita", visita);
        model.addAttribute("pageTitle", "Editar Visita");
        model.addAttribute("view", "vistas-formularios/form-visita"); // Reutilizamos el formulario
        return "layouts/layout";
    }
}