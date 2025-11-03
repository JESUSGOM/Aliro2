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

    // --- 1. MOSTRAR FORMULARIO DE NUEVA VISITA (Botón "Entrada Visitantes") ---
    @GetMapping("/visitas/entrada")
    public String mostrarFormularioEntrada(Model model) {
        model.addAttribute("visita", new Movadoj());
        model.addAttribute("pageTitle", "Registrar Entrada de Visita");
        model.addAttribute("view", "vistas-formularios/form-visita"); // Carga el fragmento del formulario
        return "layouts/layout"; // Carga la plantilla base
    }

    // --- 2. GUARDAR LA NUEVA VISITA (Acción del formulario) ---
    @PostMapping("/visitas/guardar")
    public String guardarVisita(@ModelAttribute("visita") Movadoj visita, Authentication authentication) {
        // Seteamos los datos de auditoría
        DateTimeFormatter dtfFecha = DateTimeFormatter.ofPattern("dd/MM/yy");
        DateTimeFormatter dtfHora = DateTimeFormatter.ofPattern("HH:mm");

        visita.setMovFechaEntrada(LocalDate.now().format(dtfFecha));
        visita.setMovHoraEntrada(LocalTime.now().format(dtfHora));
        // Aquí también podrías setear el centro y usuario si es necesario
        // visita.setMovCentro(usuarioLogueado.getUsuCentro());

        movadojService.save(visita);
        return "redirect:/panel"; // Vuelve al panel principal
    }

    // --- 3. MOSTRAR LISTA DE VISITAS ACTIVAS (Botón "Salida Visitantes") ---
    @GetMapping("/visitas/salida")
    public String mostrarFormularioSalida(Model model) {
        // Buscamos solo visitas que no tienen fecha de salida
        List<Movadoj> visitasActivas = movadojService.findVisitasActivas(); // Necesitarás crear este método en tu Service/Repository

        model.addAttribute("visitas", visitasActivas);
        model.addAttribute("pageTitle", "Registrar Salida de Visita");
        model.addAttribute("view", "vistas-listados/list-visitas-salida");
        return "layouts/layout";
    }

    // --- 4. REGISTRAR LA SALIDA (Acción del botón en la lista) ---
    @PostMapping("/visitas/registrar-salida/{id}")
    public String registrarSalida(@PathVariable("id") Integer id) {
        Movadoj visita = movadojService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de visita inválido:" + id));

        DateTimeFormatter dtfFecha = DateTimeFormatter.ofPattern("dd/MM/yy");
        DateTimeFormatter dtfHora = DateTimeFormatter.ofPattern("HH:mm");

        visita.setMovFechaSalida(LocalDate.now().format(dtfFecha));
        visita.setMovHoraSalida(LocalTime.now().format(dtfHora));

        movadojService.save(visita); // Guarda el cambio
        return "redirect:/visitas/salida"; // Recarga la lista de salidas
    }

    // --- 5. MOSTRAR LISTA DE TODAS LAS VISITAS (Botón "Informes Visitantes") ---
    @GetMapping("/visitas/informes")
    public String mostrarInformeVisitas(Model model) {
        model.addAttribute("visitas", movadojService.findAll());
        model.addAttribute("pageTitle", "Informe de Visitantes");
        model.addAttribute("view", "vistas-listados/list-visitas-informe");
        return "layouts/layout";
    }
}