package com.aliro2.controller;

import com.aliro2.model.Movadoj;
import com.aliro2.service.MovadojService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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

@Controller
public class VisitaController {

    @Autowired
    private MovadojService movadojService;

    @Autowired
    private UsuarioRepository usuarioRepository; // Para obtener el centro del usuario

    // --- 1. MUESTRA EL FORMULARIO DE NUEVA VISITA ---
    // Se activa con el botón: "Entrada Visitantes"
    @GetMapping("/visitas/entrada")
    public String mostrarFormularioEntrada(Model model, Authentication authentication) {

        Usuario usuario = usuarioRepository.findByUsuDni(authentication.getName()).get();

        Movadoj nuevaVisita = new Movadoj();
        nuevaVisita.setMovCentro(usuario.getUsuCentro()); // Asigna el centro del usuario

        model.addAttribute("visita", nuevaVisita);
        model.addAttribute("pageTitle", "Registrar Entrada de Visita");
        model.addAttribute("view", "vistas-formularios/form-visita");
        return "layouts/layout";
    }

    // --- 2. GUARDA LA NUEVA VISITA (O ACTUALIZA) ---
    // Se activa desde el formulario "form-visita.html"
    @PostMapping("/visitas/guardar")
    public String guardarVisita(@ModelAttribute("visita") Movadoj visita) {

        // Si es una visita nueva (no tiene ID), seteamos la fecha y hora de entrada
        if (visita.getMovOrden() == null) {
            DateTimeFormatter dtfFecha = DateTimeFormatter.ofPattern("dd/MM/yy");
            DateTimeFormatter dtfHora = DateTimeFormatter.ofPattern("HH:mm");

            visita.setMovFechaEntrada(LocalDate.now().format(dtfFecha));
            visita.setMovHoraEntrada(LocalTime.now().format(dtfHora));
        }

        movadojService.save(visita);
        return "redirect:/panel"; // Vuelve al panel principal
    }

    // --- 3. MUESTRA LISTA DE VISITAS ACTIVAS ---
    // Se activa con el botón: "Salida Visitantes"
    @GetMapping("/visitas/salida")
    public String mostrarFormularioSalida(Model model) {
        List<Movadoj> visitasActivas = movadojService.findVisitasActivas();

        model.addAttribute("visitas", visitasActivas);
        model.addAttribute("pageTitle", "Registrar Salida de Visita");
        model.addAttribute("view", "vistas-listados/list-visitas-salida");
        return "layouts/layout";
    }

    // --- 4. REGISTRA LA SALIDA (ACTUALIZA) ---
    // Se activa desde el botón en la lista "list-visitas-salida.html"
    @PostMapping("/visitas/registrar-salida/{id}")
    public String registrarSalida(@PathVariable("id") Integer id) {
        Movadoj visita = movadojService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de visita inválido:" + id));

        DateTimeFormatter dtfFecha = DateTimeFormatter.ofPattern("dd/MM/yy");
        DateTimeFormatter dtfHora = DateTimeFormatter.ofPattern("HH:mm");

        visita.setMovFechaSalida(LocalDate.now().format(dtfFecha));
        visita.setMovHoraSalida(LocalTime.now().format(dtfHora));

        movadojService.save(visita);
        return "redirect:/visitas/salida"; // Recarga la lista de salidas
    }

    // --- 5. MUESTRA EL INFORME DE TODAS LAS VISITAS ---
    // Se activa con el botón: "Informes Visitantes"
    @GetMapping("/visitas/informes")
    public String mostrarInformeVisitas(Model model) {
        model.addAttribute("visitas", movadojService.findAll()); // Muestra todas
        model.addAttribute("pageTitle", "Informe de Visitantes");
        model.addAttribute("view", "vistas-listados/list-visitas-informe");
        return "layouts/layout";
    }
}