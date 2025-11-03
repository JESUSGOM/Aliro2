package com.aliro2.controller;

import com.aliro2.model.Incidencia;
import com.aliro2.model.Usuario;
import com.aliro2.repository.UsuarioRepository;
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

@Controller
public class IncidenciaController {

    @Autowired
    private IncidenciaService incidenciaService;

    @Autowired
    private UsuarioRepository usuarioRepository; // Para obtener el usuario completo

    /**
     * Muestra el formulario para crear una NUEVA incidencia.
     * Se activa con el botón "Entrada Incidencias".
     */
    @GetMapping("/incidencias/entrada")
    public String mostrarFormularioNuevaIncidencia(Model model) {

        // Pasamos un objeto 'incidencia' vacío para que el formulario (th:object) pueda enlazarlo.
        model.addAttribute("incidencia", new Incidencia());

        // Añadimos el título de la página
        model.addAttribute("pageTitle", "Nueva Incidencia");

        // Le decimos al layout que cargue el fragmento del formulario
        model.addAttribute("view", "vistas-formularios/form-incidencia");

        return "layouts/layout"; // Siempre devolvemos la plantilla base
    }

    /**
     * Muestra el formulario para EDITAR una incidencia existente.
     * Se activa desde el botón "Editar" en la lista de incidencias.
     */
    @GetMapping("/incidencias/editar/{id}")
    public String mostrarFormularioEditarIncidencia(@PathVariable("id") Integer id, Model model) {

        // Buscamos la incidencia en la BD por su ID
        Incidencia incidencia = incidenciaService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de incidencia inválido:" + id));

        // Pasamos la incidencia encontrada al modelo
        model.addAttribute("incidencia", incidencia);
        model.addAttribute("pageTitle", "Editar Incidencia");

        // Reutilizamos el mismo fragmento de formulario
        model.addAttribute("view", "vistas-formularios/form-incidencia");

        return "layouts/layout";
    }

    /**
     * Guarda la incidencia (tanto una nueva como una editada).
     * Se activa desde el botón "Guardar" del formulario.
     */
    @PostMapping("/incidencias/guardar")
    public String guardarIncidencia(@ModelAttribute("incidencia") Incidencia incidencia, Authentication authentication) {

        // Obtenemos el DNI del usuario logueado
        String dni = authentication.getName();

        // Como tu BD usa VARCHAR para fecha/hora, las seteamos manualmente
        // NOTA: Esto es una mala práctica forzada por el diseño de la BD.
        if (incidencia.getIncId() == null) { // Solo si es nueva
            DateTimeFormatter dtfFecha = DateTimeFormatter.ofPattern("dd/MM/yy");
            DateTimeFormatter dtfHora = DateTimeFormatter.ofPattern("HH:mm");

            incidencia.setIncFecha(LocalDate.now().format(dtfFecha));
            incidencia.setIncHora(LocalTime.now().format(dtfHora));
            incidencia.setIncUsuario(dni); // Guardamos quién la creó
        }

        incidenciaService.save(incidencia);

        // Redirigimos al panel principal tras guardar
        return "redirect:/panel";
    }

    /**
     * Muestra la lista de TODAS las incidencias.
     * Se activa con los botones "Consultar Incidencias" o "Informes Incidencias".
     */
    @GetMapping({"/incidencias/consultar", "/incidencias/informes"})
    public String consultarIncidencias(Model model) {

        List<Incidencia> listaIncidencias = incidenciaService.findAll();

        model.addAttribute("incidencias", listaIncidencias);
        model.addAttribute("pageTitle", "Listado de Incidencias");

        // Le decimos al layout que cargue el fragmento de la lista
        model.addAttribute("view", "vistas-listados/list-incidencias");

        return "layouts/layout";
    }

    /**
     * Elimina una incidencia por su ID.
     * Se activa desde el botón "Eliminar" en la lista.
     */
    @GetMapping("/incidencias/eliminar/{id}")
    public String eliminarIncidencia(@PathVariable("id") Integer id) {
        incidenciaService.deleteById(id);

        // Redirigimos de vuelta a la lista
        return "redirect:/incidencias/consultar";
    }
}