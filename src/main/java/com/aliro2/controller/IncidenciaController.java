package com.aliro2.controller;

import com.aliro2.model.Incidencia;
import com.aliro2.service.IncidenciaService;
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

/**
 * Controlador para gestionar la lógica de las Incidencias (CRUD).
 * Se activa cuando el usuario hace clic en los botones del panel.
 */
@Controller
public class IncidenciaController {

    // Inyección de servicios por constructor (práctica recomendada)
    private final IncidenciaService incidenciaService;

    @Autowired
    public IncidenciaController(IncidenciaService incidenciaService) {
        this.incidenciaService = incidenciaService;
    }

    /**
     * MUESTRA EL FORMULARIO DE NUEVA INCIDENCIA
     * Se activa con el botón: "Entrada Incidencias"
     * URL: /incidencias/entrada
     */
    @GetMapping("/incidencias/entrada")
    public String mostrarFormularioNueva(Model model) {
        // Prepara un objeto 'incidencia' vacío para enlazar al formulario
        model.addAttribute("incidencia", new Incidencia());

        // Establece el título de la página
        model.addAttribute("pageTitle", "Registrar Nueva Incidencia");

        // Le dice al layout principal qué fragmento de contenido debe cargar
        model.addAttribute("view", "vistas-formularios/form-incidencia");

        return "layouts/layout"; // Carga la plantilla base (layout.html)
    }

    /**
     * MUESTRA LA LISTA DE INCIDENCIAS
     * Se activa con los botones: "Consultar Incidencias" e "Informes Incidencias"
     * URL: /incidencias/consultar y /incidencias/informes
     */
    @GetMapping({"/incidencias/consultar", "/incidencias/informes"})
    public String mostrarListaIncidencias(Model model) {
        // Busca todas las incidencias en la base de datos
        List<Incidencia> listaIncidencias = incidenciaService.findAll();

        model.addAttribute("incidencias", listaIncidencias);
        model.addAttribute("pageTitle", "Listado de Incidencias");
        model.addAttribute("view", "vistas-listados/list-incidencias");
        return "layouts/layout";
    }

    /**
     * GUARDA LA INCIDENCIA (Tanto una NUEVA como una EDITADA)
     * Se activa desde la acción POST del formulario form-incidencia.html
     * URL: /incidencias/guardar
     */
    @PostMapping("/incidencias/guardar")
    public String guardarIncidencia(@ModelAttribute("incidencia") Incidencia incidencia, Authentication authentication) {

        // Si es una incidencia nueva (no tiene ID), seteamos la fecha, hora y usuario
        if (incidencia.getIncId() == null) {
            DateTimeFormatter dtfFecha = DateTimeFormatter.ofPattern("yyyyMMdd");
            DateTimeFormatter dtfHora = DateTimeFormatter.ofPattern("HHmm");

            incidencia.setIncFecha(LocalDate.now().format(dtfFecha));
            incidencia.setIncHora(LocalTime.now().format(dtfHora));
            incidencia.setIncUsuario(authentication.getName()); // Guarda el DNI del usuario logueado
        }

        incidenciaService.save(incidencia);

        // Redirige a la lista para ver el cambio
        return "redirect:/incidencias/consultar";
    }

    /**
     * MUESTRA EL FORMULARIO PARA EDITAR UNA INCIDENCIA
     * Se activa desde el botón "Editar" en la lista de incidencias
     * URL: /incidencias/editar/{id}
     */
    @GetMapping("/incidencias/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Integer id, Model model) {

        // Busca la incidencia por su ID
        Incidencia incidencia = incidenciaService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de incidencia inválido:" + id));

        // Pasa la incidencia encontrada al formulario
        model.addAttribute("incidencia", incidencia);
        model.addAttribute("pageTitle", "Editar Incidencia");
        model.addAttribute("view", "vistas-formularios/form-incidencia"); // Reutilizamos el mismo formulario
        return "layouts/layout";
    }

    /**
     * ELIMINA UNA INCIDENCIA
     * Se activa desde el botón "Eliminar" en la lista
     * URL: /incidencias/eliminar/{id}
     */
    @GetMapping("/incidencias/eliminar/{id}")
    public String eliminarIncidencia(@PathVariable("id") Integer id) {
        incidenciaService.deleteById(id);

        // Redirige de vuelta a la lista
        return "redirect:/incidencias/consultar";
    }
}